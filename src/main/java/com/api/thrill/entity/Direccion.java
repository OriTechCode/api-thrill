package com.api.thrill.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "direcciones")
public class Direccion extends Base {

    private String calle;
    private String localidad;
    private String codpostal;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties("direcciones")
    private Usuario usuario;
}