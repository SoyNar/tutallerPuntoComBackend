package com.tutallerPuntoCom.generation.DTos.RequestDTO;

import com.tutallerPuntoCom.generation.Utils.Enums.CategoryServices;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ServiceRequestDTO(
        @NotBlank(message = "El nombre del servicio es obligatorio")
        String name,

        @NotNull(message = "La categoría del servicio es obligatoria")
        CategoryServices category,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        BigDecimal price
) {
}