package com.tutallerPuntoCom.generation.Controllers;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.WorkshopRequestDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.WorkshopResponseDTO;
import com.tutallerPuntoCom.generation.Services.Contracts.GarageServiceI;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workshops")
public class WorkshopController {

    private final GarageServiceI garageService;

    public WorkshopController(GarageServiceI garageService) {
        this.garageService = garageService;
    }

    @PostMapping
    public ResponseEntity<WorkshopResponseDTO> create(@Valid @RequestBody WorkshopRequestDTO request){

        WorkshopResponseDTO response = garageService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
