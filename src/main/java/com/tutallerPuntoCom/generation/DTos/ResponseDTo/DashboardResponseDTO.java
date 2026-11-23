package com.tutallerPuntoCom.generation.DTos.ResponseDTo;

import java.util.Map;

public record DashboardResponseDTO(
        long totalTalleresActivos,
        long totalClientes,
        long citasDelDia,
        Map<String, Long> citasPorEstado
) {}
