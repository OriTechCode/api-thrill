package com.api.thrill.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "productos")
public class Producto extends Base {

    private String nombre;
    private int cantidad;
    private double precio;
    private String descripcion;
    private String color;
    private String marca;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagen> imagenes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "subcategoria_id")
    private SubCategoria subcategoria;

    //esto es redundante por parte de la normalizacion , pero es practico para nosotros
    @ManyToOne
    private Tipo tipo;

    @OneToMany(mappedBy = "producto")
    private List<DetalleOrden> detalles;

// esto no deberia ser un one to many ?
    @OneToMany
    @JoinTable(
            name = "talle_producto",
            joinColumns = @JoinColumn(name = "id_producto"),
            inverseJoinColumns = @JoinColumn(name = "id_talle")
    )
    private List<Talle> talles;

    @ManyToMany  // en efecto, esta hecha como los dioses, dea
    @JoinTable(
            name = "descuento_producto",
            joinColumns = @JoinColumn(name = "id_producto"),
            inverseJoinColumns = @JoinColumn(name = "id_descuento")
    )
    private List<Descuento> descuentos;
}