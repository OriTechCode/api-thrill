package com.api.thrill.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "categorias")
public class Categoria extends Base {

    private String nombre;


    @ManyToMany(mappedBy = "categorias")
    private List<Producto> productos;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("categoria")
    private List<SubCategoria> subcategorias;

}