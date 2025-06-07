package com.api.thrill.service;

import com.api.thrill.entity.OrdenCompra;
import com.api.thrill.entity.enums.EstadoOrden;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.api.thrill.repository.OrdenCompraRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdenCompraService {

    private final OrdenCompraRepository ordenCompraRepository;

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
        List<PreferenceItemRequest> items = new ArrayList<>();
        orden.getDetalles().forEach(detalle -> {
            items.add(PreferenceItemRequest.builder()
                    .id(detalle.getProducto().getId().toString())
                    .title(detalle.getProducto().getNombre())
                    .description(detalle.getProducto().getDescripcion())
                    .unitPrice(BigDecimal.valueOf(detalle.getPrecio()))
                    .quantity(detalle.getCantidad())
                    .build());
        });

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("https://localhost/success")
                .failure("https://localhost/failure")
                .pending("https://localhost/pending")
                .build();

        return PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .autoReturn("approved")
                .externalReference(String.valueOf(orden.getId())) // Referencia a la orden
                .build();
    }
}