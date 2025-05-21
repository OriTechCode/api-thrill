package com.api.thrill.entity;

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
public class Categoria extends Base{

    private String nombre;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<SubCategoria> subcategorias;

}