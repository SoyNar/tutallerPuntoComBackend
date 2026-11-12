package com.tutallerPuntoCom.generation.Models;

import com.tutallerPuntoCom.generation.Utils.Enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

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
    private Status status = Status.PENDIENTE;

    @Column(nullable = false)
    private  boolean reschedule = false;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private Instant createdAt;



}
