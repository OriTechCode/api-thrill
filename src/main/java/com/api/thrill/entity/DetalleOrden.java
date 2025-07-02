package com.api.thrill.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Where(clause = "eliminado = false")
@Table(name = "detalles")
public class DetalleOrden extends Base  {

    private int cantidad;
    private double precio;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties({"detalles", "ordenes", "password" , "direcciones"})
    private Usuario usuario;


    @JoinColumn(name = "producto-talle_id")
    @JsonIgnoreProperties({"detalles"})
    private ProductoTalle productoTalle;

    @ManyToOne(optional = true) // Permitir que la relación sea opcional
    @JoinColumn(name = "orden_id")
    @JsonIgnoreProperties({"detalles"})
    private OrdenCompra orden;
}