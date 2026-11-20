package com.tutallerPuntoCom.generation.DTos.RequestDTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentRequestDTO(

        @NotNull(message = "El vehículo es obligatorio")
        Long vehicleId,

        @NotNull(message = "El taller es obligatorio")
        Long garageId,

        @NotNull(message = "El servicio es obligatorio")
        Long serviceId,

        @NotNull(message = "La fecha es obligatoria")
        @FutureOrPresent(message = "La fecha no puede estar en el pasado")
        LocalDate date,

        @NotNull(message = "La hora es obligatoria")
        LocalTime hour
) {}
