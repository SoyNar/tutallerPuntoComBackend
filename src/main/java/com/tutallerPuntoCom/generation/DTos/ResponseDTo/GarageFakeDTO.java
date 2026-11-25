package com.tutallerPuntoCom.generation.DTos.ResponseDTo;

import com.tutallerPuntoCom.generation.Models.Garage;
import com.tutallerPuntoCom.generation.Models.GarageSchedule;
import com.tutallerPuntoCom.generation.Utils.Enums.DAYS;
import com.tutallerPuntoCom.generation.Utils.Enums.GarageStatus;
import com.tutallerPuntoCom.generation.Utils.Enums.TypeVehicle;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record GarageFakeDTO(
        Long id,
        String name,
        String nit,
        String phone,
        String email,
        String address,
        String city,
        BigDecimal latitude,
        BigDecimal longitude,
        String photoUrl,
        TypeVehicle typeVehicle,
        GarageStatus garageStatus,
        List<ServiceResponseDTO> services,
        List<ScheduleFakeDTO> schedules
) {
    public static GarageFakeDTO from(
            Garage g,
            List<ServiceResponseDTO> services,
            List<ScheduleFakeDTO> schedules) {
        return new GarageFakeDTO(
                g.getId(),
                g.getName(),
                g.getNit(),
                g.getPhone(),
                g.getEmail(),
                g.getAddress(),
                g.getCity(),
                g.getLatitude(),
                g.getLongitude(),
                g.getPhotoUrl(),
                g.getTypeVehicle(),
                g.getGarageStatus(),
                services,
                schedules
        );
    }


}