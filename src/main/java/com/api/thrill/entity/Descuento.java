package com.api.thrill.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "descuentos")
public class Descuento extends Base {

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double porcentajeDesc;

    @ManyToMany(mappedBy = "descuentos")
    private List<Producto> productos;
}
