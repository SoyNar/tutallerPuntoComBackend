package com.tutallerPuntoCom.generation.DTos.RequestDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record WorkshopServicesRequestDTO(

        @NotNull(message = "La lista de servicios es obligatoria")
        @Valid
        List<Item> services
) {

    // Un servicio asignado a un taller
    public record Item(
            @NotNull(message = "El id del servicio es obligatorio")
            @Positive(message = "El id del servicio debe ser positivo")
            Long serviceId,

            @Positive(message = "El precio debe ser mayor a 0")
            @Digits(integer = 8, fraction = 2, message = "El precio admite hasta 8 enteros y 2 decimales")
            BigDecimal price
    ) {}
}