package com.tutallerPuntoCom.generation.Services.Impl;

import com.tutallerPuntoCom.generation.DTos.ResponseDTo.AvailabilityResponseDTO;
import com.tutallerPuntoCom.generation.Models.GarageSchedule;
import com.tutallerPuntoCom.generation.Models.Appointment;
import com.tutallerPuntoCom.generation.Repositories.GarageRepository;
import com.tutallerPuntoCom.generation.Repositories.GarageScheduleRepository;
import com.tutallerPuntoCom.generation.Repositories.AppointmentRepository;
import com.tutallerPuntoCom.generation.Utils.Enums.DAYS;
import com.tutallerPuntoCom.generation.Utils.Enums.AppointmentStatus;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvailabilityService {

    private final GarageRepository garageRepository;
    private final GarageScheduleRepository scheduleRepository;
    private final AppointmentRepository appointmentRepository;

    public AvailabilityService(
            GarageRepository garageRepository,
            GarageScheduleRepository scheduleRepository,
            AppointmentRepository appointmentRepository) {

        this.garageRepository = garageRepository;
        this.scheduleRepository = scheduleRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public AvailabilityResponseDTO getAvailability(
            Long garageId, LocalDate fecha) {

        garageRepository.findById(garageId)
                .orElseThrow(() ->
                        new IllegalArgumentException("El taller no existe"));

        DAYS dia = DAYS.valueOf(
                fecha.getDayOfWeek().name()
        );

        List<GarageSchedule> horarios =
                scheduleRepository.findByGarageId(garageId);

        GarageSchedule horarioDia = horarios.stream()
                .filter(h -> h.getDaysOfWeek() == dia)
                .findFirst()
                .orElse(null);

        List<LocalTime> disponibles = new ArrayList<>();

        if (horarioDia != null) {

            LocalTime hora = horarioDia.getOpenAt();
            LocalTime cierre = horarioDia.getClosesAt();

            List<Appointment> citas =
                    appointmentRepository.findByGarageIdAndDateAndStatusIn(
                            garageId,
                            fecha,
                            List.of(
                                    AppointmentStatus.PENDIENTE,
                                    AppointmentStatus.CONFIRMADA
                            )
                    );

            while (!hora.plusHours(1).isAfter(cierre)) {

                LocalTime horaActual = hora;

                boolean ocupada = citas.stream()
                        .anyMatch(cita ->
                                cita.getHour().equals(horaActual));

                if (!ocupada) {
                    disponibles.add(hora);
                }

                hora = hora.plusHours(1);
            }
        }

        return new AvailabilityResponseDTO(
                garageId,
                fecha,
                disponibles
        );
    }
}
