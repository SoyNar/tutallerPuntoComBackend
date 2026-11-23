
package com.tutallerPuntoCom.generation.DTos.RequestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ContactRequestDTO(

        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email no es válido")
        String email,

        @NotBlank(message = "El teléfono es obligatorio")
        @Pattern(regexp = "^[0-9+() -]{9,20}$",
                message = "El teléfono no es válido")
        String telefono,

        @NotBlank(message = "El mensaje no puede estar vacío")
        @Size(max = 1000, message = "El mensaje no puede superar 1000 caracteres")
        String mensaje,

        Long garageId,

        List<Long> serviceIds

) {}
