package com.api.thrill.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "usuarios")
public class Usuario extends Base {

    private String nombre;

    @Column(unique = true)
    private String email;

    private String contraseña;

    private String rol; // ADMIN / USER

    @OneToMany(mappedBy = "usuario")
    private List<OrdenCompra> ordenes;


    @OneToMany
    @JoinTable(
            name = "usuario_direccion",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_direccion")
    )
    private List<Direccion> direcciones;
}
