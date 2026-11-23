package com.tutallerPuntoCom.generation.DTos.ResponseDTo;

import java.util.List;

public record RegisterResponseDTO(String token, UserResponseDTO user, List<VehicleResponseDTO> vehicles) {

//    public static RegisterResponseDTO from()
}
