package com.tutallerPuntoCom.generation.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "garage_service", uniqueConstraints = @UniqueConstraint(columnNames = {"garage_id","service_id"}))
public class GarageService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "garage_id",nullable = false)
    private Garage garage;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "service_id",nullable = false)
    private OfferService offerService;

    @Column(precision = 10,scale = 2)
    private BigDecimal price;

}
