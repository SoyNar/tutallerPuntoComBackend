package com.tutallerPuntoCom.generation.Services.Impl;

import com.tutallerPuntoCom.generation.DTos.RequestDTO.VehicleRequestDto;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.VehicleResponseDTO;
import com.tutallerPuntoCom.generation.Models.User;
import com.tutallerPuntoCom.generation.Models.Vehicle;
import com.tutallerPuntoCom.generation.Repositories.UserRepository;
import com.tutallerPuntoCom.generation.Repositories.VehicleRepository;
import com.tutallerPuntoCom.generation.Services.Contracts.VehicleServiceI;
import org.springframework.stereotype.Service;

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
    public VehicleResponseDTO createVehicle(VehicleRequestDto request, String userEmail) {
        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String normalizedPlate = request.plate().trim().toUpperCase();
        if (vehicleRepository.existsByPlateIgnoreCase(normalizedPlate)) {
            throw new RuntimeException("Ya existe un vehículo registrado con esa placa");
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(request.brand().trim());
        vehicle.setTypeVehicle(request.typeVehicle());
        vehicle.setModel(request.model().trim());
        vehicle.setPlate(normalizedPlate);
        vehicle.setUser(user);

        Vehicle saved = vehicleRepository.save(vehicle);
        return VehicleResponseDTO.from(saved);
    }

    @Override
    public List<VehicleResponseDTO> getMyVehicles(String userEmail) {
        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return vehicleRepository.findByUserId(user.getId()).stream()
                .map(VehicleResponseDTO::from)
                .toList();
    }

    @Override
    public VehicleResponseDTO getVehicleById(Long id, String userEmail) {
        Vehicle vehicle = findOwnedVehicle(id, userEmail);
        return VehicleResponseDTO.from(vehicle);
    }

    @Override
    public VehicleResponseDTO updateVehicle(Long id, VehicleRequestDto request, String userEmail) {
        Vehicle vehicle = findOwnedVehicle(id, userEmail);

        String normalizedPlate = request.plate().trim().toUpperCase();
        if (!normalizedPlate.equalsIgnoreCase(vehicle.getPlate())
                && vehicleRepository.existsByPlateIgnoreCase(normalizedPlate)) {
            throw new RuntimeException("Ya existe un vehículo registrado con esa placa");
        }

        vehicle.setBrand(request.brand().trim());
        vehicle.setTypeVehicle(request.typeVehicle());
        vehicle.setModel(request.model().trim());
        vehicle.setPlate(normalizedPlate);

        Vehicle updated = vehicleRepository.save(vehicle);
        return VehicleResponseDTO.from(updated);
    }

    @Override
    public void deleteVehicle(Long id, String userEmail) {
        Vehicle vehicle = findOwnedVehicle(id, userEmail);
        vehicleRepository.delete(vehicle);
    }

    private Vehicle findOwnedVehicle(Long id, String userEmail) {
        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        if (vehicle.getUser() == null || !vehicle.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No tienes permiso para acceder a este vehículo");
        }

        return vehicle;
    }
}
