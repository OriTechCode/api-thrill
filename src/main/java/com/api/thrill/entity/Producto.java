package com.api.thrill.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "productos")
public class Producto extends Base {

    private String nombre;
    private double precio;
    private String descripcion;
    private int cantidad= 0;
    private String color;
    private String marca;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagen> imagenes = new ArrayList<>();


    @ManyToMany()
    @JoinTable(
            name = "categoria-producto",
            joinColumns = @JoinColumn(name = "id_producto"),
            inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    @JsonIgnoreProperties("productos")
    private List<Categoria> categorias;

    //esto es redundante por parte de la normalizacion , pero es practico para nosotros
    @ManyToOne
    @JsonIgnoreProperties("talles")
    private Tipo tipo;


    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"producto", "detalles"})
    private List<ProductoTalle> productoTalles = new ArrayList<>();

    @ManyToMany  // en efecto, esta hecha como los dioses, dea
    @JoinTable(
            name = "descuento_producto",
            joinColumns = @JoinColumn(name = "id_producto"),
            inverseJoinColumns = @JoinColumn(name = "id_descuento")
    )
    private List<Descuento> descuentos;

    public List<Long> getCategoriaIds() {
        if (categorias == null || categorias.isEmpty()) {
            return new ArrayList<>();
        }
        return categorias.stream()
                .map(Categoria::getId)
                .collect(Collectors.toList());
    }

}