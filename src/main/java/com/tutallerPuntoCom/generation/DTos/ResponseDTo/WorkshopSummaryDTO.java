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
    public static WorkshopSummaryDTO from(Garage g){
        return new WorkshopSummaryDTO(
                g.getId(),
                g.getName(),
                g.getCity(),
                g.getAddress(),
                g.getPhone(),
                g.getLatitude(),
                g.getLongitude(),
                g.getPhotoUrl(),
                g.getTypeVehicle()
        );
    }
}
