package com.api.thrill.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference
    private Producto producto;

//a nivel normalizacion esta mal , a nivel practica nos conviene
    @ManyToOne
    @JsonBackReference
    private Tipo tipo;
}
