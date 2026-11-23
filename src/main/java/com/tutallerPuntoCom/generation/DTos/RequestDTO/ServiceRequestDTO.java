package com.tutallerPuntoCom.generation.DTos.RequestDTO;


import com.tutallerPuntoCom.generation.Utils.Enums.CategoryServices;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ServiceRequestDTO(
        @NotBlank String name,
        @NotNull CategoryServices categoryServices,
        @NotNull @DecimalMin(value = "0.01", inclusive = true) BigDecimal price
) {}