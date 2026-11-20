package com.tutallerPuntoCom.generation.Controllers;



import com.tutallerPuntoCom.generation.DTos.RequestDTO.ServiceRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.CategoryResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.ServiceResponseDTO;
import com.tutallerPuntoCom.generation.Services.Contracts.OfferServiceI;
import com.tutallerPuntoCom.generation.Utils.Enums.CategoryServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OfferServiceController {

    private final OfferServiceI offerService;

    public OfferServiceController(OfferServiceI offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/services")
    public ResponseEntity<List<ServiceResponseDTO>> getAllServices(
            @RequestParam(required = false) CategoryServices category
    ) {
        return ResponseEntity.ok(offerService.getAllServices(category));
    }

    @GetMapping("/services/{id}")
    public ResponseEntity<ServiceResponseDTO> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(offerService.getServiceById(id));
    }

    @GetMapping("/service-categories")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        return ResponseEntity.ok(offerService.getAllCategories());
    }

    @PostMapping("/services")
    public ResponseEntity<ServiceResponseDTO> createService(
            @Valid @RequestBody ServiceRequestDTO request
    ) {
        ServiceResponseDTO created = offerService.createService(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/services/{id}")
    public ResponseEntity<ServiceResponseDTO> updateService(
            @PathVariable Long id,
            @Valid @RequestBody ServiceRequestDTO request
    ) {
        return ResponseEntity.ok(offerService.updateService(id, request));
    }

    @DeleteMapping("/services/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        offerService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
