package com.tutallerPuntoCom.generation.DTos.RequestDTO;

import com.tutallerPuntoCom.generation.Utils.Enums.GarageStatus;
import jakarta.validation.constraints.NotNull;

public record WorkshopStatusRequestDTO(

        @NotNull(message = "El estado es obligatorio")
        GarageStatus garageStatus
) {}
