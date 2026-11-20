package com.tutallerPuntoCom.generation.Repositories;

import com.tutallerPuntoCom.generation.Models.Appointment;
import com.tutallerPuntoCom.generation.Utils.Enums.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>, JpaSpecificationExecutor<Appointment> {

    List<Appointment> findByGarageIdAndDateAndStatusIn(Long garageId, LocalDate date, Collection<AppointmentStatus> statuses);

    long countByGarageIdAndDateAndHourAndStatusIn(Long garageId, LocalDate date, LocalTime hour, Collection<AppointmentStatus> statuses);

    boolean existsByVehicleIdAndDateAndHourAndStatusIn(Long vehicleId, LocalDate date, LocalTime hour, Collection<AppointmentStatus> statuses);

    Page<Appointment> findByVehicleUserId(Long userId, Pageable pageable);

    Page<Appointment> findByVehicleUserIdAndStatus(Long userId, AppointmentStatus status, Pageable pageable);

    long countByVehicleUserIdAndStatusIn(Long userId, Collection<AppointmentStatus> statuses);
}
