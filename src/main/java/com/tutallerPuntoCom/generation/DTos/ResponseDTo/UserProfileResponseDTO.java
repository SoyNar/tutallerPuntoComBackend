package com.tutallerPuntoCom.generation.DTos.ResponseDTo;

import com.tutallerPuntoCom.generation.Models.User;

import java.util.List;

public record UserProfileResponseDTO(Long id,
                                     String name,
                                     String lastname,
                                     String email,
                                     String address,
                                     List<VehicleResponseDTO> vehicles,
                                     String phone
) {
    public static UserProfileResponseDTO from(User user){

        List<VehicleResponseDTO> vehicles = user.getVehicles().stream().map(VehicleResponseDTO::from).toList();
        return  new UserProfileResponseDTO(
                user.getId(),
                user.getName(),
                user.getLastname(),
                user.getEmail(),
                user.getAddress(),
                vehicles,
                user.getPhone()

        );
    }
}
