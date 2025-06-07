

package com.api.thrill.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "producto_talle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoTalle extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Producto producto;

    @ManyToOne
    private Talle talle;

    private int stock;
}
