package com.tutallerPuntoCom.generation.Services.Contracts;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.WorkshopRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.WorkshopResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.WorkshopSummaryDTO;
import com.tutallerPuntoCom.generation.Utils.Enums.TypeVehicle;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface GarageServiceI {

    WorkshopResponseDTO create(WorkshopRequestDTO request);

    // Listar y buscar con filtro de paginacion
    PageResponseDTO<WorkshopSummaryDTO> findAll(
            String city,
            Long serviceId,
            TypeVehicle vehicleType,
            Pageable pageable);

    // Consultar por su ID
    WorkshopResponseDTO  findById(Long id);


}
