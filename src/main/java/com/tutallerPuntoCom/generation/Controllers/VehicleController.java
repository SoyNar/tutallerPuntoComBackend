package com.tutallerPuntoCom.generation.Controllers;




import com.tutallerPuntoCom.generation.DTos.RequestDTO.VehicleRequestDto;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.VehicleResponseDTO;
import com.tutallerPuntoCom.generation.Services.Contracts.VehicleServiceI;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleServiceI vehicleService;

    public VehicleController(VehicleServiceI vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> createVehicle(
            @Valid @RequestBody VehicleRequestDto request,
            Authentication authentication
    ) {
        VehicleResponseDTO created = vehicleService.createVehicle(request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/me")
    public ResponseEntity<List<VehicleResponseDTO>> getMyVehicles(Authentication authentication) {
        return ResponseEntity.ok(vehicleService.getMyVehicles(authentication.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getVehicleById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id, authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> updateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody VehicleRequestDto request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, request, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(
            @PathVariable Long id,
            Authentication authentication
    ) {
        vehicleService.deleteVehicle(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}