

package com.api.thrill.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "producto_talle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductoTalle extends Base {


    @ManyToOne

    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties({"productoTalles", "tipo"})
    private Producto producto;

    @ManyToOne
    @JsonIgnoreProperties("productoTalles")
    @JoinColumn(name = "talle_id")
    private Talle talle;

    private int stock;
}
