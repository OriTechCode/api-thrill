package com.api.thrill.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "direcciones")
public class Direccion extends Base {

    private String calle;
    private String localidad;
    private String codpostal;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference // Indica que esta referencia no debe ser serializada en JSON
    private Usuario usuario;
}