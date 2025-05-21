package com.api.thrill.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "talles")
public class Talle extends Base {

    private String talle;

    @ManyToOne
    private Producto producto;

//a nivel normalizacion esta mal , a nivel practica nos conviene
    @ManyToOne
    private Tipo tipo;
}
