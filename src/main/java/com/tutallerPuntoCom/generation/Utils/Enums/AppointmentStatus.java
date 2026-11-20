package com.tutallerPuntoCom.generation.Utils.Enums;

import java.util.Set;

public enum AppointmentStatus {

    PENDIENTE,
    CONFIRMADA,
    COMPLETADA,
    CANCELADA;

    // Para consultas de capacidad y solapamiento.
    public static final Set<AppointmentStatus> ACTIVE = Set.of(PENDIENTE, CONFIRMADA);

    // Controla el flujo de transicion.
    public boolean canTransitionTo(AppointmentStatus next) {
        if (next == null) {
            return false;
        }
        return switch (this) {
            case PENDIENTE  -> next == CONFIRMADA || next == CANCELADA;
            case CONFIRMADA -> next == COMPLETADA || next == CANCELADA;
            case COMPLETADA, CANCELADA -> false;
        };
    }

    // Confirma los estados donde no es posible transicionar.
    public boolean isFinal() {
        return this == COMPLETADA || this == CANCELADA;
    }

    // Indica si esta activa la cita.
    public boolean isActive() {
        return ACTIVE.contains(this);
    }
}