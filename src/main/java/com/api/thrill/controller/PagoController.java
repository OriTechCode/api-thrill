package com.api.thrill.controller;

import com.api.thrill.dto.PaymentResponse;
import com.api.thrill.entity.OrdenCompra;
import com.api.thrill.service.MercadoPagoService;
import com.api.thrill.service.OrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @Autowired
    private OrdenCompraService ordenCompraService;

    @PostMapping("/crear/{ordenId}")
    public ResponseEntity<PaymentResponse> crearPago(@PathVariable Long ordenId) {
        try {
            OrdenCompra orden = ordenCompraService.findById(ordenId)
                    .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + ordenId));

            PaymentResponse response = mercadoPagoService.createPayment(orden);

            if ("error".equals(response.getStatus())) {
                return ResponseEntity.badRequest().body(response);
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            PaymentResponse errorResponse = new PaymentResponse();
            errorResponse.setStatus("error");
            errorResponse.setErrorMessage("Error al crear el pago: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebhook(@RequestBody(required = false) Map<String, Object> body) {
        try {
            if (body == null) {
                return ResponseEntity.badRequest().body("Body vacío");
            }

            String type = body.get("type") != null ? body.get("type").toString() :
                    body.get("action") != null ? body.get("action").toString() : null;

            if (type == null) {
                return ResponseEntity.badRequest().body("Tipo de notificación no especificado");
            }

            String dataId = null;
            if (body.containsKey("data")) {
                @SuppressWarnings("unchecked")
                Map<String, Object> data = (Map<String, Object>) body.get("data");
                if (data.containsKey("id")) {
                    dataId = data.get("id").toString();
                }
            }

            if (dataId == null) {
                return ResponseEntity.badRequest().body("ID de datos no encontrado");
            }

            mercadoPagoService.processWebhook(type, dataId);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error procesando webhook: " + e.getMessage());
        }
    }


    @GetMapping("/estado/{ordenId}")
    public ResponseEntity<?> getEstadoPago(@PathVariable Long ordenId) {
        try {
            OrdenCompra orden = ordenCompraService.findById(ordenId)
                    .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + ordenId));

            return ResponseEntity.ok(Map.of(
                    "estado", orden.getEstadoOrden(),
                    "metodoPago", orden.getMetodoPago()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener estado del pago: " + e.getMessage());
        }
    }
}