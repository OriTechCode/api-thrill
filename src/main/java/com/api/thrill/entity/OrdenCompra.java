package com.api.thrill.entity;

import com.api.thrill.entity.enums.EstadoOrden;
import com.api.thrill.entity.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "direccion_id")
    private Direccion direccion;


    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    private List<DetalleOrden> detalles;


}