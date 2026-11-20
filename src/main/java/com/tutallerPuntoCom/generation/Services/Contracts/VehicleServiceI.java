package com.tutallerPuntoCom.generation.Services.Contracts;



import com.tutallerPuntoCom.generation.DTos.RequestDTO.VehicleRequestDto;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.VehicleResponseDTO;

import java.util.List;

public interface VehicleServiceI {

    VehicleResponseDTO createVehicle(VehicleRequestDto request, String userEmail);

    List<VehicleResponseDTO> getMyVehicles(String userEmail);

    VehicleResponseDTO getVehicleById(Long id, String userEmail);

    VehicleResponseDTO updateVehicle(Long id, VehicleRequestDto request, String userEmail);

    void deleteVehicle(Long id, String userEmail);
}