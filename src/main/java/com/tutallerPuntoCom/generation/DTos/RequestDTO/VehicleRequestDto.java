package com.tutallerPuntoCom.generation.DTos.RequestDTO;

import com.tutallerPuntoCom.generation.Utils.Enums.TypeVehicle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleRequestDto(
        @NotBlank
        String brand,
        @NotNull
        TypeVehicle typeVehicle,
        @NotBlank
        String model,
        @NotBlank
        String plate
        ) {
}
