package com.tutallerPuntoCom.generation.DTos.RequestDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;


public record RegisterUserRequestDTo (
        @NotBlank
        String name,
        @NotBlank
        String email,
        String lastname,
        @NotBlank
        @NotBlank
        @Size(min = 8, message = "La clave debe tener al menos 8 caracteres")
        String password,
        @NotBlank
        String address,
        String phone,
        @Valid
        @NotNull
        List<VehicleRequestDto> vehicles
) {
}
