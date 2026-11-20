package com.tutallerPuntoCom.generation.DTos.ResponseDTo;

import com.tutallerPuntoCom.generation.Models.Vehicle;
import com.tutallerPuntoCom.generation.Utils.Enums.TypeVehicle;

public record VehicleResponseDTO(
        Long id,
        String brand,
        TypeVehicle typeVehicle,
        String model,
        String plate,
        Long userId
) {
    public static VehicleResponseDTO from(Vehicle vehicle) {
        return new VehicleResponseDTO(
                vehicle.getId(),
                vehicle.getBrand(),
                vehicle.getTypeVehicle(),
                vehicle.getModel(),
                vehicle.getPlate(),
                vehicle.getUser() != null ? vehicle.getUser().getId() : null
        );
    }
}