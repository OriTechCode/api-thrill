package com.api.thrill.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "detalles")
public class DetalleOrden extends Base  {

    private int cantidad;
    private double precio;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "orden_id")
    private OrdenCompra orden;
}