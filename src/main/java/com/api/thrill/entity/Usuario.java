package com.api.thrill.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String email;

    private String contraseña;

    private String rol; // ADMIN / USER

    @OneToMany(mappedBy = "usuario")
    private List<OrdenCompra> ordenes;

    @ManyToMany
    @JoinTable(
            name = "usuario_direccion",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_direccion")
    )
    private List<Direccion> direcciones;
}
