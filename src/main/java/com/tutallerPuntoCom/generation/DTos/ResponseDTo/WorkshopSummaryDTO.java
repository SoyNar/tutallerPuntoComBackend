package com.tutallerPuntoCom.generation.DTos.ResponseDTo;

import com.tutallerPuntoCom.generation.Models.Garage;
import com.tutallerPuntoCom.generation.Utils.Enums.TypeVehicle;

import java.math.BigDecimal;

public record WorkshopSummaryDTO(
        Long id,
        String name,
        String city,
        String address,
        String phone,
        BigDecimal latitude,
        BigDecimal longitude,
        String photoUrl,
        TypeVehicle typeVehicle
){
    public static WorkshopSummaryDTO from(Garage garage){
        return new WorkshopSummaryDTO(
                garage.getId(),
                garage.getName(),
                garage.getCity(),
                garage.getAddress(),
                garage.getPhone(),
                garage.getLatitude(),
                garage.getLongitude(),
                garage.getPhotoUrl(),
                garage.getTypeVehicle()
        );
    }
}
