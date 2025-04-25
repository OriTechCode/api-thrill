package com.api.thrill.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "direcciones")
public class Direccion extends Base  {

    private String calle;
    private String localidad;
    private String codpostal;


    @ManyToOne
    private Usuario usuario;

}
