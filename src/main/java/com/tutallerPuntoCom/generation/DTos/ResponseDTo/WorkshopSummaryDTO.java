package com.tutallerPuntoCom.generation.DTos.ResponseDTo;

import com.tutallerPuntoCom.generation.Models.Garage;
import com.tutallerPuntoCom.generation.Utils.Enums.TypeVehicle;

import java.math.BigDecimal;
import java.util.List;

public record WorkshopSummaryDTO(
        Long id,
        String name,
        String city,
        String address,
        String photoUrl,
        TypeVehicle typeVehicle,
        boolean openNow,
        List<String> services
) {
    public static WorkshopSummaryDTO from(Garage g, boolean openNow, List<String> services) {
        return new WorkshopSummaryDTO(
                g.getId(),
                g.getName(),
                g.getCity(),
                g.getAddress(),
                g.getPhotoUrl(),
                g.getTypeVehicle(),
                openNow,
                services
        );
    }
}
