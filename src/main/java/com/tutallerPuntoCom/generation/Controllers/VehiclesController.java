    package com.tutallerPuntoCom.generation.Controllers;


    import com.tutallerPuntoCom.generation.DTos.RequestDTO.VehicleRequestDto;
    import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
    import com.tutallerPuntoCom.generation.DTos.ResponseDTo.VehicleResponseDTO;
    import com.tutallerPuntoCom.generation.Security.AuthUser;
    import com.tutallerPuntoCom.generation.Services.Contracts.VehicleServiceI;
    import io.swagger.v3.oas.annotations.security.SecurityRequirement;
    import jakarta.validation.Valid;
    import org.springdoc.core.annotations.ParameterObject;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.web.PageableDefault;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.security.core.annotation.AuthenticationPrincipal;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/api/vehicles")
    @PreAuthorize("hasRole('CLIENT')")
    @SecurityRequirement(name = "bearerAuth")
    public class VehiclesController {

        private final VehicleServiceI vehicleService;

        public VehiclesController(VehicleServiceI vehicleService) {
            this.vehicleService = vehicleService;
        }

        @PostMapping
        public ResponseEntity<VehicleResponseDTO> createVehicle(
                @AuthenticationPrincipal AuthUser user,
                @Valid @RequestBody VehicleRequestDto request
        ) {
            VehicleResponseDTO created = vehicleService.createVehicle(request, user.id());
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        }

        @GetMapping("/me")
        @PreAuthorize("hasAnyRole('CLIENTE')")
        public ResponseEntity<PageResponseDTO> getMyVehicles(
                @AuthenticationPrincipal AuthUser user,
                @ParameterObject @PageableDefault(page = 0, size = 10) Pageable pageable
        ) {
            return ResponseEntity.ok(vehicleService.getMyVehicles(user.id(),pageable));
        }

        @GetMapping("/{id}")
        @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
        public ResponseEntity<VehicleResponseDTO> getVehicleById(
                @PathVariable Long id,
                @AuthenticationPrincipal AuthUser user
        ) {
            return ResponseEntity.ok(vehicleService.getVehicleById(id, user));
        }

        @PutMapping("/{id}")
        public ResponseEntity<VehicleResponseDTO> updateVehicle(
                @PathVariable Long id,
                @AuthenticationPrincipal AuthUser user,
                @Valid @RequestBody VehicleRequestDto request
        ) {
            return ResponseEntity.ok(vehicleService.updateVehicle(id, request, user.id()));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteVehicle(
                @PathVariable Long id,
                @AuthenticationPrincipal AuthUser user
        ) {
            vehicleService.deleteVehicle(id, user.id());
            return ResponseEntity.noContent().build();
        }
    }
