package com.tutallerPuntoCom.generation.DTos.RequestDTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record RescheduleRequestDTO(

        @NotNull(message = "La fecha es obligatoria")
        @FutureOrPresent(message = "La fecha no puede estar en el pasado")
        LocalDate date,

        @NotNull(message = "La hora es obligatoria")
        LocalTime hour
) {}
