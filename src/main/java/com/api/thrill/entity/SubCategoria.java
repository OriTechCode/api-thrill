package com.api.thrill.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "subcategorias")
public class SubCategoria extends Base {

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonIgnoreProperties("subcategorias")
    private Categoria categoria;

  //  @OneToMany(mappedBy = "subcategoria", cascade = CascadeType.ALL)
  //  @JsonIgnoreProperties("subcategoria")
  //  private List<Producto> productos;
}