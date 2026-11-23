package com.tutallerPuntoCom.generation.Services.Impl;


import com.tutallerPuntoCom.generation.DTos.RequestDTO.VehicleRequestDto;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.VehicleResponseDTO;
import com.tutallerPuntoCom.generation.Exceptions.Customs.BadRequestException;
import com.tutallerPuntoCom.generation.Exceptions.Customs.ConflictException;
import com.tutallerPuntoCom.generation.Exceptions.Customs.ForbiddenException;
import com.tutallerPuntoCom.generation.Exceptions.Customs.NotFoundModelException;
import com.tutallerPuntoCom.generation.Models.User;
import com.tutallerPuntoCom.generation.Models.Vehicle;
import com.tutallerPuntoCom.generation.Repositories.UserRepository;
import com.tutallerPuntoCom.generation.Repositories.VehicleRepository;
import com.tutallerPuntoCom.generation.Security.AuthUser;
import com.tutallerPuntoCom.generation.Services.Contracts.VehicleServiceI;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleServiceI {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public VehicleResponseDTO createVehicle(VehicleRequestDto request, Long userId) {
        String normalizedPlate = request.plate().trim().toUpperCase();

        if (vehicleRepository.existsByPlateIgnoreCase(normalizedPlate)) {
            throw new ConflictException("Ya existe un vehículo registrado con esa placa");
        }

        User owner = userRepository.getReferenceById(userId);

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(request.brand().trim());
        vehicle.setTypeVehicle(request.typeVehicle());
        vehicle.setModel(request.model().trim());
        vehicle.setPlate(normalizedPlate);
        vehicle.setUser(owner);

            Vehicle saved = vehicleRepository.saveAndFlush(vehicle);
            return VehicleResponseDTO.from(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<VehicleResponseDTO> getMyVehicles(Long userId, Pageable pageable) {
       Page<Vehicle> vehicles = vehicleRepository.findByUserId(userId, pageable);
       return  PageResponseDTO.from(vehicles.map(VehicleResponseDTO::from));
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleResponseDTO getVehicleById(Long id, AuthUser user) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundModelException("Vehículo no encontrado"));

        boolean isAdmin = "ADMIN".equalsIgnoreCase(user.role()) || "ROLE_ADMIN".equalsIgnoreCase(user.role());
        boolean isOwner = vehicle.getUser() != null && user.id().equals(vehicle.getUser().getId());

        if (!isAdmin && !isOwner) {
            throw new ForbiddenException("No tienes permiso para acceder a este vehículo");
        }

        return VehicleResponseDTO.from(vehicle);
    }

    @Override
    public VehicleResponseDTO updateVehicle(Long id, VehicleRequestDto request, Long userId) {
        Vehicle vehicle = findOwnedVehicle(id, userId);
        String normalizedPlate = request.plate().trim().toUpperCase();

        if (!normalizedPlate.equalsIgnoreCase(vehicle.getPlate())
                && vehicleRepository.existsByPlateIgnoreCase(normalizedPlate)) {
            throw new ConflictException("Ya existe un vehículo registrado con esa placa");
        }

        vehicle.setBrand(request.brand().trim());
        vehicle.setTypeVehicle(request.typeVehicle());
        vehicle.setModel(request.model().trim());
        vehicle.setPlate(normalizedPlate);

        return VehicleResponseDTO.from(vehicleRepository.saveAndFlush(vehicle));
    }

    @Override
    public void deleteVehicle(Long id, Long userId) {
        Vehicle vehicle = findOwnedVehicle(id, userId);
        try {
            vehicleRepository.delete(vehicle);
            vehicleRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("No se puede eliminar el vehículo porque tiene citas asociadas");
        }
    }

    private Vehicle findOwnedVehicle(Long id, Long userId) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundModelException("Vehículo no encontrado"));

        if (vehicle.getUser() == null || !userId.equals(vehicle.getUser().getId())) {
            throw new ForbiddenException("No tienes permiso para acceder a este vehículo");
        }
        return vehicle;
    }


    @Override
    @Transactional
    public List<VehicleResponseDTO> createVehicles(List<VehicleRequestDto> requests, Long userId) {
        List<String> placas = requests.stream()
                .map(r -> r.plate().trim().toUpperCase())
                .toList();

        if (placas.stream().distinct().count() != placas.size()) {
            throw new ConflictException("Hay placas repetidas en la lista");
        }

        if (vehicleRepository.existsByPlateIn(placas)) {
            throw new ConflictException("Una o más placas ya están registradas");
        }

        User owner = userRepository.getReferenceById(userId);

        List<Vehicle> vehiculos = requests.stream()
                .map(r -> {
                    Vehicle v = new Vehicle();
                    v.setTypeVehicle(r.typeVehicle());
                    v.setBrand(r.brand());
                    v.setModel(r.model());
                    v.setPlate(r.plate().trim().toUpperCase());
                    v.setUser(owner);
                    return v;
                })
                .toList();

        return vehicleRepository.saveAll(vehiculos).stream()
                .map(VehicleResponseDTO::from)
                .toList();
    }
}