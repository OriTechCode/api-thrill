package com.api.thrill.entity;

import com.api.thrill.entity.enums.EstadoOrden;
import com.api.thrill.entity.enums.MetodoPago;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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

    private BigDecimal costoEnvio;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties({"ordenes","detalles" , "direcciones"})
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "direccion_id")
    @JsonIgnoreProperties({"ordenes", "usuario"})
    private Direccion direccion;

    @OneToMany(mappedBy = "orden")
    @JsonIgnoreProperties({"orden", "usuario"})  // Agregamos "usuario" aquí
    private List<DetalleOrden> detalles = new ArrayList<>(); // Inicializamos la lista

    // Método modificado para evitar duplicados
    public void addDetalle(DetalleOrden detalle) {
        if (detalles == null) {
            detalles = new ArrayList<>();
        }
        
        // Verificar si el detalle ya existe en la colección
        boolean existe = false;
        for (DetalleOrden d : detalles) {
            if (d.equals(detalle) || 
                (detalle.getId() != null && d.getId() != null && d.getId().equals(detalle.getId()))) {
                existe = true;
                break;
            }
        }
        
        // Solo agregar si no existe
        if (!existe) {
            detalles.add(detalle);
            
            // Establecer la relación inversa solo si es necesario
            if (detalle.getOrden() != this) {
                detalle.setOrden(this);
            }
        }
    }
}