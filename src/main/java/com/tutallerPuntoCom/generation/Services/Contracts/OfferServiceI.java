package com.tutallerPuntoCom.generation.Services.Contracts;


import com.tutallerPuntoCom.generation.DTos.RequestDTO.ServiceRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.CategoryResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.ServiceResponseDTO;
import com.tutallerPuntoCom.generation.Utils.Enums.CategoryServices;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface OfferServiceI {
    PageResponseDTO<ServiceResponseDTO> getAllServices(CategoryServices categoria, Pageable pageable);
    ServiceResponseDTO getServiceById(Long id);
    ServiceResponseDTO createService(ServiceRequestDTO request);
    ServiceResponseDTO updateService(Long id, ServiceRequestDTO request);
    void deleteService(Long id);
    List<CategoryResponseDTO> getCategories();
}
