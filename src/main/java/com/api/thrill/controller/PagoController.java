package com.api.thrill.controller;

import com.api.thrill.entity.OrdenCompra;
import com.api.thrill.service.OrdenCompraService;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PagoController {
    private final OrdenCompraService ordenCompraService;
    @Value("${mercadopago.access.token}")
    private String mercadoPagoAccessToken;
    /**
     * Crear nueva orden y generar preferencia de MercadoPago.
     */
    @PostMapping("/crear")
    public ResponseEntity<Map<String, Object>> crearOrden(@RequestBody OrdenCompra orden) throws Exception {

        MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

        OrdenCompra ordenCreada = ordenCompraService.crearOrden(orden);

        // Volvés a crear la preferencia para obtener el init_point
        PreferenceClient client = new PreferenceClient();
        PreferenceRequest preferenceRequest = ordenCompraService.construirPreferencia(ordenCreada);
        Preference preference = client.create(preferenceRequest);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("orden", ordenCreada);
        respuesta.put("init_point", preference.getInitPoint());

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }


    /**
     * Obtener el estado de la orden de compra.
     */
    @GetMapping("/estado/{id}")
    public ResponseEntity<Map<String, String>> getEstado(@PathVariable Long id) {
        return ordenCompraService.obtenerOrdenPorId(id)
                .map(orden -> ResponseEntity.ok(Map.of(
                        "estado", orden.getEstadoOrden() != null ? orden.getEstadoOrden() : "Sin estado",
                        "metodoPago", orden.getMetodoPago() != null ? orden.getMetodoPago() : "Sin método"
                )))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Orden no encontrada con ID: " + id)));
    }



    /**
     * Consultar todas las órdenes.
     */
    @GetMapping("")
    public ResponseEntity<?> listarOrdenes() {
        List<OrdenCompra> ordenes = ordenCompraService.listarOrdenes();
        return ResponseEntity.ok(ordenes);
    }

    /**
     * Procesar una notificación de webhook de MercadoPago.
     */
    @PostMapping("/webhook")
    public ResponseEntity<String> procesarWebhook(@RequestBody Map<String, Object> body) {
        MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

        try {
            if (body != null && body.containsKey("type") && body.containsKey("data")) {
                String type = body.get("type").toString();
                String dataId = ((Map<String, Object>) body.get("data")).get("id").toString();

                ordenCompraService.procesarWebhook(type, dataId);
                return ResponseEntity.ok("Webhook procesado correctamente.");
            }
            return ResponseEntity.badRequest().body("El body no tiene el formato esperado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error procesando webhook: " + e.getMessage());
        }
    }
}