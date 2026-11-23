package com.tutallerPuntoCom.generation.Models;

import com.tutallerPuntoCom.generation.Utils.Enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@Entity
@Table(name = "appointments",uniqueConstraints = {@UniqueConstraint(name = "uq_cita_franja_activa",columnNames = {"garage_id","date","hour","active_slot"})})

public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Setter(lombok.AccessLevel.NONE)
    @Column(name = "active_slot")
    private Integer activeSlot = 1;

    @PrePersist
    @PreUpdate
    private void syncActiveSlot() {
        if (this.status == AppointmentStatus.PENDIENTE || this.status == AppointmentStatus.CONFIRMADA) {
            this.activeSlot = 1;
        } else {
            this.activeSlot = null;
        }
    }


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "vehicle_id",nullable = false)
    private  Vehicle vehicle;

    //taller_id
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "garage_id", nullable = false)
    private Garage garage;

    //service_id
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private  OfferService service;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime hour;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status = AppointmentStatus.PENDIENTE;

    @Column(nullable = false)
    private  boolean reschedule = false;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Instant createdAt;

}
