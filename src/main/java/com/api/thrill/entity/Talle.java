package com.api.thrill.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Talle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String talle;
//a nivel normalizacion esta mal , a nivel practica nos conviene , que hacemos ?
    @ManyToOne
    private Tipo tipo;
}
