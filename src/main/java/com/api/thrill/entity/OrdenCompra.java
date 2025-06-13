package com.api.thrill.entity;

import com.api.thrill.entity.enums.EstadoOrden;
import com.api.thrill.entity.enums.MetodoPago;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "ordenes")
public class OrdenCompra extends Base {

    private LocalDateTime fecha;
    private int cantidad;
    private double total;

    @Column(name = "metodo_pago")
    private String metodoPago;

    private String estadoOrden;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties({"ordenes","detalles"})
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "direccion_id")
    @JsonIgnoreProperties({"ordenes", "usuario"})
    private Direccion direccion;

    @OneToMany(mappedBy = "orden")
    @JsonIgnoreProperties({"orden", "usuario"})  // Agregamos "usuario" aquí
    private List<DetalleOrden> detalles = new ArrayList<>(); // Inicializamos la lista

    // Método helper para mantener la consistencia bidireccional
    public void addDetalle(DetalleOrden detalle) {
        if (detalles == null) {
            detalles = new ArrayList<>();
        }
        detalles.add(detalle);
        detalle.setOrden(this);
    }
}