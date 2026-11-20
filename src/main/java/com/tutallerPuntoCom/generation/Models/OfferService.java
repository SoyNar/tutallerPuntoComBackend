package com.tutallerPuntoCom.generation.Models;

import com.tutallerPuntoCom.generation.Utils.Enums.CategoryServices;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "services")
public class OfferService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryServices categoryServices;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal price;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant createdAt;



}
