package com.tutallerPuntoCom.generation.DTos.RequestDTO;

import com.tutallerPuntoCom.generation.Utils.Enums.TypeVehicle;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record WorkshopRequestDTO(
        @NotBlank(message = "El nombre del taller es obligatorio")
        String name,

        @NotBlank(message = "El NIT es obligatorio")
        String nit,

        @NotBlank(message = "El teléfono es obligatorio")
        @Pattern(regexp = "^[0-9]{7,10}$", message = "El teléfono debe tener entre 7 y 10 dígitos")
        String phone,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El formato del correo no es válido")
        String email,

        @NotBlank(message = "La dirección es obligatoria")
        String address,

        @NotBlank(message = "La ciudad es obligatoria")
        String city,

        @NotNull(message = "La latitud es obligatoria")
        @DecimalMin(value = "-90.0", message = "La latitud debe estar entre -90 y 90")
        @DecimalMax(value = "90.0", message = "La latitud debe estar entre -90 y 90")
        BigDecimal latitude,

        @NotNull(message = "La longitud es obligatoria")
        @DecimalMin(value = "-180.0", message = "La longitud debe estar entre -180 y 180")
        @DecimalMax(value = "180.0", message = "La longitud debe estar entre -180 y 180")
        BigDecimal longitude,

        String photoUrl,

        @NotNull(message = "El tipo de vehículo es obligatorio")
        TypeVehicle typeVehicle
) {
        private static String clean(String value) {
                return value == null ? null : value.trim();
        }

        // Constructor compato para limpiar los datos que entran
        public WorkshopRequestDTO {
                name = clean(name);
                nit = clean(nit);
                phone = clean(phone);
                email = email == null ? null : email.trim().toLowerCase();
                address = clean(address);
                city = clean(city);
        }

}