package com.tutallerPuntoCom.generation.Services.Contracts;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.WorkshopRequestDTO;
import com.tutallerPuntoCom.generation.DTos.RequestDTO.WorkshopStatusRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.WorkshopResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.WorkshopSummaryDTO;
import com.tutallerPuntoCom.generation.Utils.Enums.TypeVehicle;
import org.springframework.data.domain.Pageable;

public interface GarageServiceI {

    //Crear un taller
    WorkshopResponseDTO create(WorkshopRequestDTO request);

    // Listar y buscar con filtro de paginacion
    PageResponseDTO<WorkshopSummaryDTO> findAll(
            String city,
            Long serviceId,
            TypeVehicle vehicleType,
            Pageable pageable);

    // Consultar por su ID
    WorkshopResponseDTO  findById(Long id);

    // Actualizar datos de un taller
    WorkshopResponseDTO update(Long id, WorkshopRequestDTO request);

    // Actualizar solo el estado de un taller
    WorkshopResponseDTO changeStatus(Long id, WorkshopStatusRequestDTO request);

    // Borrado logico que es solo dejar el taller en SUSPENDIDO
    void delete(Long id);
}
