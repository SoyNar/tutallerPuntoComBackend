package com.tutallerPuntoCom.generation.DTos.ResponseDTo;

import com.tutallerPuntoCom.generation.Models.Appointment;
import com.tutallerPuntoCom.generation.Utils.Enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentResponseDTO(
        Long id,
        Long vehicleId,
        String vehiclePlate,
        Long garageId,
        String garageName,
        Long serviceId,
        String serviceName,
        LocalDate date,
        LocalTime hour,
        AppointmentStatus status
) {
    public static AppointmentResponseDTO from(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getVehicle().getId(),
                appointment.getVehicle().getPlate(),
                appointment.getGarage().getId(),
                appointment.getGarage().getName(),
                appointment.getService().getId(),
                appointment.getService().getName(),
                appointment.getDate(),
                appointment.getHour(),
                appointment.getStatus()
        );
    }
}
