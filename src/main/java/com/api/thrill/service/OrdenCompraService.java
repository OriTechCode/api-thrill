package com.api.thrill.service;

import com.api.thrill.entity.OrdenCompra;
import com.api.thrill.entity.enums.EstadoOrden;
import com.api.thrill.repository.OrdenCompraRepository;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdenCompraService {

    private final OrdenCompraRepository ordenCompraRepository;
    @Value("${mercadopago.access.token}")
    private String mercadoPagoAccessToken;



    @Getter
    private String ultimoInitPoint;

    /**
     * Crear una nueva orden y una preferencia en MercadoPago.
     */
    public OrdenCompra crearOrden(OrdenCompra orden) throws Exception {
        orden.setEstadoOrden(EstadoOrden.PENDIENTE.toString());

        // Crear preferencia de MercadoPago
        PreferenceClient client = new PreferenceClient();
        PreferenceRequest preferenceRequest = construirPreferencia(orden);
        Preference preference = client.create(preferenceRequest);
        ultimoInitPoint = preference.getInitPoint();

        orden.setEstadoOrden(EstadoOrden.PENDIENTE.toString());
        orden.setMetodoPago("MERCADO_PAGO"); // Set método explícito
        ordenCompraRepository.save(orden); // Guardar orden antes de retornar resultados

        return orden;
    }


    public Optional<OrdenCompra> obtenerOrdenPorId(Long id) {
        return ordenCompraRepository.findById(id);
    }


    public List<OrdenCompra> listarOrdenes() {
        return ordenCompraRepository.findAll();
    }

    public void procesarWebhook(String type, String dataId) throws Exception {
        if ("payment".equals(type)) {
            PaymentClient paymentClient = new PaymentClient();
            Payment payment = paymentClient.get(Long.parseLong(dataId));

            String externalReference = payment.getExternalReference(); // Id referencia
            OrdenCompra orden = ordenCompraRepository.findById(Long.parseLong(externalReference))
                    .orElseThrow(() -> new Exception("Orden no encontrada"));

            switch (payment.getStatus()) {
                case "approved" -> orden.setEstadoOrden(EstadoOrden.PAGADO.toString());
                case "pending" -> orden.setEstadoOrden(EstadoOrden.PENDIENTE.toString());
                case "rejected" -> orden.setEstadoOrden(EstadoOrden.CANCELADO.toString());
                default -> orden.setEstadoOrden(EstadoOrden.PENDIENTE.toString());
            }

            ordenCompraRepository.save(orden);
        }
    }

    public PreferenceRequest construirPreferencia(OrdenCompra orden) {

        // Lista de ítems a comprar (productos en el carrito)
        List<PreferenceItemRequest> items = new ArrayList<>();

        orden.getDetalles().forEach(detalle -> {
            items.add(PreferenceItemRequest.builder()
                    .id(detalle.getProductoTalle().getId().toString()) // ID del producto (interno)
                    .title(detalle.getProductoTalle().getProducto().getNombre()) // Nombre visible del producto
                    .description(detalle.getProductoTalle().getProducto().getDescripcion()) // Descripción visible
                    .unitPrice(BigDecimal.valueOf(detalle.getPrecio())) // Precio unitario
                    .quantity(detalle.getCantidad()) // Cantidad
                    .currencyId("ARS") // Código de moneda ISO (ej: USD, ARS, BRL)
                    .build());
        });
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("https://youtu.be/dQw4w9WgXcQ?si=-Axp2WQ3zUkxCQjc")
                .failure("https://www.youtube.com/watch?v=lOg-0rEkWjw")
                .pending("https://www.youtube.com/watch?v=lOg-0rEkWjw")
                .build();
        // Construcción final de la preferencia
        return PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .autoReturn("approved") // Redirige automáticamente si el pago es aprobado
                .externalReference(String.valueOf(orden.getId())) // ID de la orden como referencia cruzada
                .notificationUrl("https://2ec8-38-51-31-185.ngrok-free.app/api/pagos/webhook") // 👈 Webhook para actualizaciones
                .build();
    }

}