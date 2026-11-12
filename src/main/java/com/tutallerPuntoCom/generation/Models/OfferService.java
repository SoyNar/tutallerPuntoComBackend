package com.tutallerPuntoCom.generation.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "services")
public class OfferService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
