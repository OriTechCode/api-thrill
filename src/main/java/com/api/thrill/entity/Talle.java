package com.api.thrill.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "talles")
public class Talle extends Base {

    private String talle;

    @OneToMany(mappedBy = "talle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("talle")
    private List<ProductoTalle> productoTalles = new ArrayList<>();


//a nivel normalizacion esta mal , a nivel practica nos conviene
    @ManyToOne
    @JsonIgnoreProperties("talles")
    private Tipo tipo;
}
