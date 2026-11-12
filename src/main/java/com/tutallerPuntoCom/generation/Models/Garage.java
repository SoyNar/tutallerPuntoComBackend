package com.tutallerPuntoCom.generation.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "garage")
public class Garage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

}
