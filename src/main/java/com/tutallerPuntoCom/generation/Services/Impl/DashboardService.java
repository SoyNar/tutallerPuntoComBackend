package com.tutallerPuntoCom.generation.Services.Impl;

import com.tutallerPuntoCom.generation.DTos.ResponseDTo.DashboardResponseDTO;
import com.tutallerPuntoCom.generation.Repositories.GarageRepository;
import com.tutallerPuntoCom.generation.Repositories.UserRepository;
import com.tutallerPuntoCom.generation.Repositories.AppointmentRepository;
import com.tutallerPuntoCom.generation.Utils.Enums.GarageStatus;
import com.tutallerPuntoCom.generation.Utils.Enums.Roles;
import com.tutallerPuntoCom.generation.Utils.Enums.AppointmentStatus;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final GarageRepository garageRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    public DashboardService(
            GarageRepository garageRepository,
            UserRepository userRepository,
            AppointmentRepository appointmentRepository) {

        this.garageRepository = garageRepository;
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public DashboardResponseDTO getDashboard() {

        long talleresActivos = garageRepository.findAll().stream()
                .filter(g -> g.getGarageStatus() == GarageStatus.ACTIVO)
                .count();

        long clientes = userRepository.findAll().stream()
                .filter(u -> u.getRole() == Roles.CLIENTE)
                .count();

        LocalDate hoy = LocalDate.now();

        var citas = appointmentRepository.findAll();

        long citasDelDia = citas.stream()
                .filter(c -> c.getDate().equals(hoy))
                .count();

        Map<String, Long> citasPorEstado = new HashMap<>();

        for (AppointmentStatus estado : AppointmentStatus.values()) {
            long cantidad = citas.stream()
                    .filter(c -> c.getStatus() == estado)
                    .count();

            citasPorEstado.put(estado.name(), cantidad);
        }

        return new DashboardResponseDTO(
                talleresActivos,
                clientes,
                citasDelDia,
                citasPorEstado
        );
    }
}
