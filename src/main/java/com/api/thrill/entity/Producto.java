package com.api.thrill.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int cantidad;
    private double precio;
    private String descripcion;
    private String color;
    private String marca;

    @ManyToOne
    private Categoria categoria;

    @ManyToOne
    private Tipo tipo;

    @OneToMany(mappedBy = "producto")
    private List<DetalleOrden> detalles;

    @ManyToMany
    @JoinTable(
            name = "talle_producto",
            joinColumns = @JoinColumn(name = "id_producto"),
            inverseJoinColumns = @JoinColumn(name = "id_talle")
    )
    private List<Talle> talles;

    @ManyToMany
    @JoinTable(
            name = "descuento_producto",
            joinColumns = @JoinColumn(name = "id_producto"),
            inverseJoinColumns = @JoinColumn(name = "id_descuento")
    )
    private List<Descuento> descuentos;
}