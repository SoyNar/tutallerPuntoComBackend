package com.tutallerPuntoCom.generation.Services.Contracts;


import com.tutallerPuntoCom.generation.DTos.RequestDTO.VehicleRequestDto;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.VehicleResponseDTO;
import com.tutallerPuntoCom.generation.Security.AuthUser;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VehicleServiceI {
    VehicleResponseDTO createVehicle(VehicleRequestDto request, Long userId);
    PageResponseDTO<VehicleResponseDTO> getMyVehicles(Long userId, Pageable pageable);
    VehicleResponseDTO getVehicleById(Long id, AuthUser user);
    VehicleResponseDTO updateVehicle(Long id, VehicleRequestDto request, Long userId);
    void deleteVehicle(Long id, Long userId);
}