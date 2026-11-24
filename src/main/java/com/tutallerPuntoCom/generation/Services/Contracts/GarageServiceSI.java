package com.tutallerPuntoCom.generation.Services.Contracts;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.WorkshopServicesRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.ServiceResponseDTO;

import java.util.List;

public interface GarageServiceSI {

    // Contratos

    // Traer los servicios de un taller
    List<ServiceResponseDTO> findByGarage(Long garageId);

    // Remplazar todos los servicios de un taller
    List<ServiceResponseDTO> replace(Long garageId, WorkshopServicesRequestDTO request);
}
