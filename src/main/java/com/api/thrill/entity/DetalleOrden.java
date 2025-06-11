package com.api.thrill.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "detalles")
public class DetalleOrden extends Base  {

    private int cantidad;
    private double precio;

    @ManyToOne
    @JoinColumn(name = "producto-talle_id")
    @JsonIgnoreProperties("detalles")
    private ProductoTalle productoTalle;

    @ManyToOne(optional = true) // Permitir que la relación sea opcional
    @JoinColumn(name = "orden_id")
    private OrdenCompra orden;
}