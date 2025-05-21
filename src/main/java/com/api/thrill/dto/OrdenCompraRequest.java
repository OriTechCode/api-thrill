package com.api.thrill.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenCompraRequest {

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El ID de la dirección es obligatorio")
    private Long direccionId;

    @NotEmpty(message = "Debe haber al menos un producto en la orden")
    private List<DetalleOrdenRequest> detalles;

    @NotNull(message = "El método de pago es obligatorio")
    private String metodoPago; // "EFECTIVO", "MERCADO_PAGO"
}