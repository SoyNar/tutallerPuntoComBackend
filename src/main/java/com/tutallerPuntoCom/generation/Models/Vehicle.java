package com.tutallerPuntoCom.generation.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
}
