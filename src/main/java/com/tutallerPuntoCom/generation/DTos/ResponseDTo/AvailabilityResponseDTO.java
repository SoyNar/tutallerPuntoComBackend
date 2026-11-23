package com.tutallerPuntoCom.generation.DTos.ResponseDTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record AvailabilityResponseDTO(
        Long garageId,
        LocalDate fecha,
        List<LocalTime> horariosDisponibles
) {}
