package com.tutallerPuntoCom.generation.DTos.ResponseDTo;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.ScheduleDTO;
import com.tutallerPuntoCom.generation.Models.Garage;
import com.tutallerPuntoCom.generation.Utils.Enums.GarageStatus;
import com.tutallerPuntoCom.generation.Utils.Enums.TypeVehicle;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record WorkshopResponseDTO(
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
        Instant createdAt,
        List<ScheduleDTO> schedule,
        List<ServiceResponseDTO> services
) {
    public static WorkshopResponseDTO from(Garage g, List<ScheduleDTO> schedule, List<ServiceResponseDTO> services) {
        return new WorkshopResponseDTO(
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
                g.getCreatedAt(),
                schedule,
                services
        );
    }
}