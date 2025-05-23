package com.api.thrill.service;

import com.api.thrill.dto.PaymentResponse;
import com.api.thrill.entity.DetalleOrden;
import com.api.thrill.entity.OrdenCompra;
import com.api.thrill.entity.enums.EstadoOrden;
import com.api.thrill.entity.enums.MetodoPago;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoService {

    @Value("${app.url.frontend}")
    private String frontendUrl;

    @Autowired
    private OrdenCompraService ordenCompraService;

    public PaymentResponse createPayment(OrdenCompra orden) {
        PaymentResponse response = new PaymentResponse();
        try {
            // Actualizar estado inicial de la orden
            orden.setEstadoOrden(EstadoOrden.PENDIENTE.toString());
            orden.setMetodoPago(MetodoPago.MERCADOPAGO.toString());
            ordenCompraService.save(orden);

            PreferenceClient client = new PreferenceClient();

            // Crear items para la preferencia
            List<PreferenceItemRequest> items = createPreferenceItems(orden);

            // Configurar URLs de retorno
            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success(frontendUrl + "/payment/success")
                    .failure(frontendUrl + "/payment/failure")
                    .pending(frontendUrl + "/payment/pending")
                    .build();

            // Configurar el pagador
            PreferencePayerRequest payer = PreferencePayerRequest.builder()
                    .name(orden.getUsuario().getUsername())
                    .email(orden.getUsuario().getEmail())
                    .build();

            // Crear la preferencia completa
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .autoReturn("approved")
                    .binaryMode(true)
                    .externalReference(orden.getId().toString())
                    .payer(payer)
                    .notificationUrl(frontendUrl + "/api/pagos/webhook")
                    .build();

            var preference = client.create(preferenceRequest);

            response.setInitPoint(preference.getInitPoint());
            response.setStatus("success");
            response.setPreferenceId(preference.getId());
            response.setOrdenId(orden.getId());

            return response;

        } catch (MPException | MPApiException e) {
            orden.setEstadoOrden(EstadoOrden.CANCELADO.toString());
            ordenCompraService.save(orden);
            response.setStatus("error");
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    private List<PreferenceItemRequest> createPreferenceItems(OrdenCompra orden) {
        List<PreferenceItemRequest> items = new ArrayList<>();

        for (DetalleOrden detalle : orden.getDetalles()) {
            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title(detalle.getProducto().getNombre())
                    .quantity(detalle.getCantidad())
                    .unitPrice(BigDecimal.valueOf(detalle.getPrecio()))
                    .currencyId("ARS")
                    .description("Producto ID: " + detalle.getProducto().getId())
                    .pictureUrl(detalle.getProducto().getImagenes().isEmpty() ? null :
                            detalle.getProducto().getImagenes().get(0).getUrl())
                    .build();
            items.add(item);
        }

        return items;
    }

    public void processWebhook(String type, String dataId) {
        System.out.println("Iniciando processWebhook - Type: " + type + ", DataId: " + dataId);

        if ("payment".equals(type)) {
            try {
                System.out.println("Creando PaymentClient...");
                PaymentClient paymentClient = new PaymentClient();

                System.out.println("Convirtiendo dataId a Long...");
                Long paymentId = Long.parseLong(dataId);
                System.out.println("PaymentId convertido: " + paymentId);

                System.out.println("Obteniendo pago de MercadoPago...");
                Payment payment = paymentClient.get(paymentId);
                System.out.println("Pago obtenido: " + (payment != null));

                if (payment != null && payment.getExternalReference() != null) {
                    System.out.println("ExternalReference: " + payment.getExternalReference());
                    String ordenId = payment.getExternalReference();

                    System.out.println("Convirtiendo ordenId a Long...");
                    Long ordenIdLong = Long.parseLong(ordenId);
                    System.out.println("OrdenId convertido: " + ordenIdLong);

                    System.out.println("Buscando orden en la base de datos...");
                    OrdenCompra orden = ordenCompraService.findById(ordenIdLong)
                            .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

                    System.out.println("Actualizando estado de la orden...");
                    updateOrdenStatus(orden, payment.getStatus());
                    System.out.println("Nuevo estado de la orden: " + orden.getEstadoOrden());

                    System.out.println("Guardando orden actualizada...");
                    ordenCompraService.save(orden);
                    System.out.println("Proceso completado exitosamente");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error al convertir número: " + e.getMessage());
                throw new RuntimeException("Error de conversión numérica: " + e.getMessage(), e);
            } catch (MPException | MPApiException e) {
                System.err.println("Error de MercadoPago: " + e.getMessage());
                throw new RuntimeException("Error procesando webhook de MercadoPago: " + e.getMessage(), e);
            } catch (Exception e) {
                System.err.println("Error inesperado: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Error inesperado: " + e.getMessage(), e);
            }
        } else {
            System.out.println("Tipo de webhook no soportado: " + type);
        }
    }


    private void updateOrdenStatus(OrdenCompra orden, String paymentStatus) {
        switch (paymentStatus) {
            case "approved":
                orden.setEstadoOrden(EstadoOrden.PAGADO.toString());
                break;
            case "rejected":
                orden.setEstadoOrden(EstadoOrden.CANCELADO.toString());
                break;
            case "pending":
                orden.setEstadoOrden(EstadoOrden.PENDIENTE.toString());
                break;
            case "in_process":
                orden.setEstadoOrden(EstadoOrden.EN_PROCESO.toString());
                break;
            default:
                orden.setEstadoOrden(EstadoOrden.PENDIENTE.toString());
        }
    }
}