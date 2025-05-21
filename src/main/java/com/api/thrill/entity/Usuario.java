package com.api.thrill.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "usuarios")
public class Usuario extends Base {

    private String nombre;

    @Column(unique = true)
    private String email;

    private String contrasena;

    private String rol; // ADMIN / USER

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imagen_id")
    private Imagen imagenPerfil;

    @OneToMany(mappedBy = "usuario")
    private List<OrdenCompra> ordenes;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Direccion> direcciones;
}
