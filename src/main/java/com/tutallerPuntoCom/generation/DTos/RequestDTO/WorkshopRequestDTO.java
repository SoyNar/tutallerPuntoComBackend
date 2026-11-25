package com.tutallerPuntoCom.generation.DTos.RequestDTO;

import com.tutallerPuntoCom.generation.Utils.Enums.GarageStatus;
import com.tutallerPuntoCom.generation.Utils.Enums.TypeVehicle;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

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
        @DecimalMin(value = "6.08", message = "La ubicación debe estar dentro del Valle de Aburrá")
        @DecimalMax(value = "6.45", message = "La ubicación debe estar dentro del Valle de Aburrá")
        BigDecimal latitude,

        @NotNull(message = "La longitud es obligatoria")
        @DecimalMin(value = "-75.72", message = "La ubicación debe estar dentro del Valle de Aburrá")
        @DecimalMax(value = "-75.72", message = "La ubicación debe estar dentro del Valle de Aburrá")
        BigDecimal longitude,

        String photoUrl,

        @NotNull(message = "El tipo de vehículo es obligatorio")
        TypeVehicle typeVehicle,

        @NotNull(message = "El estado es obligatorio")
        GarageStatus garageStatus,

        @NotEmpty(message = "Debes indicar al menos un día de atención")
        @Valid
        List<ScheduleDTO> schedule,

        @NotEmpty(message = "Debes asignar al menos un servicio")
        @Valid
        List<WorkshopServicesRequestDTO.Item> services

) {
        // Constructor compato para limpiar los datos que entran
        public WorkshopRequestDTO {
                name = clean(name);
                nit = clean(nit);
                phone = clean(phone);
                email = email == null ? null : email.trim().toLowerCase();
                address = clean(address);
                city = clean(city);
        }

        private static String clean(String value) {
                return value == null ? null : value.trim();
        }

}