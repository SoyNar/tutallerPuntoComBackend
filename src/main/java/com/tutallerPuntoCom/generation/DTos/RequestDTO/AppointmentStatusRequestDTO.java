package com.tutallerPuntoCom.generation.DTos.RequestDTO;

import com.tutallerPuntoCom.generation.Utils.Enums.AppointmentStatus;
import jakarta.validation.constraints.NotNull;

public record AppointmentStatusRequestDTO(

        @NotNull(message = "El estado es obligatorio")
        AppointmentStatus status
) {}
