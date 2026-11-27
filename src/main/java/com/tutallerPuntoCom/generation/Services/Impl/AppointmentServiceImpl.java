package com.tutallerPuntoCom.generation.Services.Impl;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.AppointmentRequestDTO;
import com.tutallerPuntoCom.generation.DTos.RequestDTO.AppointmentStatusRequestDTO;
import com.tutallerPuntoCom.generation.DTos.RequestDTO.RescheduleRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.AppointmentResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.CountResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
import com.tutallerPuntoCom.generation.Exceptions.Customs.BusinessRuleException;
import com.tutallerPuntoCom.generation.Exceptions.Customs.ConflictException;
import com.tutallerPuntoCom.generation.Exceptions.Customs.ForbiddenException;
import com.tutallerPuntoCom.generation.Exceptions.Customs.NotFoundModelException;
import com.tutallerPuntoCom.generation.Models.Appointment;
import com.tutallerPuntoCom.generation.Models.Garage;
import com.tutallerPuntoCom.generation.Models.GarageService;
import com.tutallerPuntoCom.generation.Models.User;
import com.tutallerPuntoCom.generation.Models.Vehicle;
import com.tutallerPuntoCom.generation.Repositories.AppointmentRepository;
import com.tutallerPuntoCom.generation.Repositories.GarageRepository;
import com.tutallerPuntoCom.generation.Repositories.GarageServiceRepository;
import com.tutallerPuntoCom.generation.Repositories.UserRepository;
import com.tutallerPuntoCom.generation.Repositories.VehicleRepository;
import com.tutallerPuntoCom.generation.Services.Contracts.AppointmentServiceI;
import com.tutallerPuntoCom.generation.Services.Contracts.EmailServiceI;
import com.tutallerPuntoCom.generation.Utils.Enums.AppointmentStatus;
import com.tutallerPuntoCom.generation.Utils.Enums.DAYS;
import com.tutallerPuntoCom.generation.Utils.Enums.GarageStatus;
import com.tutallerPuntoCom.generation.Utils.Enums.Roles;
import com.tutallerPuntoCom.generation.Utils.Enums.TypeVehicle;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentServiceI {

    private static final ZoneId ZONE = ZoneId.of("America/Bogota");
    private static final long MIN_HOURS_TO_BOOK = 2;
    private static final long MIN_HOURS_TO_CHANGE = 24;
    private static final long SLOT_CAPACITY = 1;

    private final AppointmentRepository appointmentRepository;
    private final VehicleRepository vehicleRepository;
    private final GarageRepository garageRepository;
    private final GarageServiceRepository garageServiceRepository;
    private final UserRepository userRepository;
    private final EmailServiceI emailService;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  VehicleRepository vehicleRepository,
                                  GarageRepository garageRepository,
                                  GarageServiceRepository garageServiceRepository,
                                  UserRepository userRepository,
                                  EmailServiceI emailService) {
        this.appointmentRepository = appointmentRepository;
        this.vehicleRepository = vehicleRepository;
        this.garageRepository = garageRepository;
        this.garageServiceRepository = garageServiceRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public AppointmentResponseDTO create(Long userId, AppointmentRequestDTO request) {
        Vehicle vehicle = vehicleRepository.findById(request.vehicleId())
                .orElseThrow(() -> new NotFoundModelException("El vehículo no existe"));
        if (!vehicle.getUser().getId().equals(userId)) {
            throw new ForbiddenException("El vehículo no pertenece al usuario");
        }

        Garage garage = garageRepository.findById(request.garageId())
                .orElseThrow(() -> new NotFoundModelException("El taller no existe"));
        if (garage.getGarageStatus() != GarageStatus.ACTIVO) {
            throw new BusinessRuleException("El taller no está activo");
        }
        if (garage.getTypeVehicle() != TypeVehicle.AMBOS && garage.getTypeVehicle() != vehicle.getTypeVehicle()) {
            throw new BusinessRuleException("El taller no atiende este tipo de vehículo");
        }

        GarageService garageService = garageServiceRepository
                .findByGarageIdAndOfferServiceId(garage.getId(), request.serviceId())
                .orElseThrow(() -> new BusinessRuleException("El taller no ofrece este servicio"));

        validateSlot(garage, vehicle, request.date(), request.hour());

        Appointment appointment = new Appointment();
        appointment.setVehicle(vehicle);
        appointment.setGarage(garage);
        appointment.setService(garageService.getOfferService());
        appointment.setDate(request.date());
        appointment.setHour(request.hour());

        Appointment saved = appointmentRepository.save(appointment);
        notify(saved, "Tu cita fue agendada", "appointment-confirmed");
        return AppointmentResponseDTO.from(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<AppointmentResponseDTO> findMyAppointments(Long userId, AppointmentStatus status, Pageable pageable) {
        Page<Appointment> page = status == null
                ? appointmentRepository.findByVehicleUserId(userId, pageable)
                : appointmentRepository.findByVehicleUserIdAndStatus(userId, status, pageable);
        return PageResponseDTO.from(page.map(AppointmentResponseDTO::from));
    }

    @Override
    @Transactional(readOnly = true)
    public CountResponseDTO countMyAppointments(Long userId) {
        return new CountResponseDTO(appointmentRepository.countByVehicleUserIdAndStatusIn(userId, AppointmentStatus.ACTIVE));
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponseDTO findById(Long id, Long userId) {
        return AppointmentResponseDTO.from(findForOwnerOrAdmin(id, userId));
    }

    @Override
    public AppointmentResponseDTO cancel(Long id, Long userId) {
        Appointment appointment = findForOwnerOrAdmin(id, userId);
        validateTimeToChange(appointment);
        changeStatus(appointment, AppointmentStatus.CANCELADA);

        notify(appointment, "Tu cita fue cancelada", "appointment-cancelled");
        return AppointmentResponseDTO.from(appointment);
    }

    @Override
    public AppointmentResponseDTO reschedule(Long id, Long userId, RescheduleRequestDTO request) {
        Appointment appointment = findAppointment(id);
        if (!isOwner(appointment, userId)) {
            throw new ForbiddenException("No tienes permiso sobre esta cita");
        }
        if (appointment.getStatus().isFinal()) {
            throw new ConflictException("Una cita completada o cancelada no se puede reprogramar");
        }
        validateTimeToChange(appointment);
        if (appointment.getDate().equals(request.date()) && appointment.getHour().equals(request.hour())) {
            throw new BusinessRuleException("La nueva fecha y hora son iguales a las actuales");
        }

        validateSlot(appointment.getGarage(), appointment.getVehicle(), request.date(), request.hour());

        appointment.setDate(request.date());
        appointment.setHour(request.hour());
        appointment.setReschedule(true);

        notify(appointment, "Tu cita fue reprogramada", "appointment-rescheduled");
        return AppointmentResponseDTO.from(appointment);
    }

    @Override
    public AppointmentResponseDTO updateStatus(Long id, AppointmentStatusRequestDTO request) {
        Appointment appointment = findAppointment(id);
        changeStatus(appointment, request.status());

        if (request.status() == AppointmentStatus.CONFIRMADA) {
            notify(appointment, "Tu cita fue confirmada", "appointment-confirmed");
        } else if (request.status() == AppointmentStatus.CANCELADA) {
            notify(appointment, "Tu cita fue cancelada", "appointment-cancelled");
        }
        return AppointmentResponseDTO.from(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<AppointmentResponseDTO> findAll(Long garageId, LocalDate date, AppointmentStatus status, Pageable pageable) {
        Specification<Appointment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (garageId != null) {
                predicates.add(cb.equal(root.get("garage").get("id"), garageId));
            }
            if (date != null) {
                predicates.add(cb.equal(root.get("date"), date));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return PageResponseDTO.from(appointmentRepository.findAll(spec, pageable).map(AppointmentResponseDTO::from));
    }

    private void validateSlot(Garage garage, Vehicle vehicle, LocalDate date, LocalTime hour) {
        LocalDateTime start = LocalDateTime.of(date, hour);
        if (start.isBefore(LocalDateTime.now(ZONE).plusHours(MIN_HOURS_TO_BOOK))) {
            throw new BusinessRuleException("La cita debe agendarse con mínimo " + MIN_HOURS_TO_BOOK + " horas de anticipación");
        }

        DAYS day = DAYS.values()[date.getDayOfWeek().getValue() - 1];
        boolean insideSchedule = garage.getGarageSchedules().stream()
                .anyMatch(s -> s.getDaysOfWeek() == day
                        && !hour.isBefore(s.getOpenAt())
                        && hour.isBefore(s.getClosesAt()));
        if (!insideSchedule) {
            throw new BusinessRuleException("La hora está fuera del horario del taller");
        }

        long taken = appointmentRepository.countByGarageIdAndDateAndHourAndStatusIn(
                garage.getId(), date, hour, AppointmentStatus.ACTIVE);
        if (taken >= SLOT_CAPACITY) {
            throw new ConflictException("La franja horaria ya está ocupada");
        }

        if (appointmentRepository.existsByVehicleIdAndDateAndHourAndStatusIn(
                vehicle.getId(), date, hour, AppointmentStatus.ACTIVE)) {
            throw new ConflictException("El vehículo ya tiene una cita en esa franja");
        }
    }

    private void validateTimeToChange(Appointment appointment) {
        LocalDateTime start = LocalDateTime.of(appointment.getDate(), appointment.getHour());
        if (start.isBefore(LocalDateTime.now(ZONE).plusHours(MIN_HOURS_TO_CHANGE))) {
            throw new BusinessRuleException("Solo se puede cancelar o reprogramar con más de " + MIN_HOURS_TO_CHANGE + " horas de anticipación");
        }
    }

    private void changeStatus(Appointment appointment, AppointmentStatus next) {
        if (!appointment.getStatus().canTransitionTo(next)) {
            throw new ConflictException("No se puede pasar la cita de " + appointment.getStatus() + " a " + next);
        }
        appointment.setStatus(next);
    }

    private Appointment findAppointment(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundModelException("La cita no existe"));
    }

    private Appointment findForOwnerOrAdmin(Long id, Long userId) {
        Appointment appointment = findAppointment(id);
        if (isOwner(appointment, userId)) {
            return appointment;
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundModelException("El usuario no existe"));
        if (user.getRole() != Roles.ADMIN) {
            throw new ForbiddenException("No tienes permiso sobre esta cita");
        }
        return appointment;
    }

    private boolean isOwner(Appointment appointment, Long userId) {
        return appointment.getVehicle().getUser().getId().equals(userId);
    }

    private void notify(Appointment appointment, String subject, String template) {
        try {
            User client = appointment.getVehicle().getUser();
            emailService.sendTemplate(client.getEmail(), subject, template, Map.of(
                    "name", client.getName(),
                    "workshop", appointment.getGarage().getName(),
                    "date", appointment.getDate() + " " + appointment.getHour()
            ));
        } catch (RuntimeException ignored) {
        }
    }
}
