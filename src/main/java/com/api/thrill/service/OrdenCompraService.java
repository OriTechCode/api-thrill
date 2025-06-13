package com.api.thrill.service;

import com.api.thrill.entity.OrdenCompra;
import com.api.thrill.entity.enums.EstadoOrden;
import com.api.thrill.repository.OrdenCompraRepository;
import com.api.thrill.repository.ProductoTalleRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import com.api.thrill.entity.DetalleOrden;

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

    private final ProductoTalleRepository productoTalleRepository;
    @Getter
    private String mercadoPagoPublicKey;

    @Getter
    private String ultimoInitPoint;

    /**
     * Crear una nueva orden y una preferencia en MercadoPago.
     */
    @Autowired
    private DetalleOrdenService detalleOrdenService;

    public OrdenCompra crearOrden(OrdenCompra orden) throws Exception {
        orden.setEstadoOrden(EstadoOrden.PENDIENTE.toString());
        
        // Guardamos primero la orden
        OrdenCompra ordenGuardada = ordenCompraRepository.save(orden);
        
        // Actualizamos los detalles existentes
        if (orden.getDetalles() != null) {
            for (DetalleOrden detalle : orden.getDetalles()) {
                Optional<DetalleOrden> detalleExistente = detalleOrdenService.findById(detalle.getId());
                if (detalleExistente.isPresent()) {
                    DetalleOrden detalleActual = detalleExistente.get();
                    detalleActual.setOrden(ordenGuardada);
                    detalleOrdenService.update(detalleActual.getId(), detalleActual);
                }
            }
        }

        // Crear preferencia de MercadoPago
        PreferenceClient client = new PreferenceClient();
        PreferenceRequest preferenceRequest = construirPreferencia(ordenGuardada);
        Preference preference = client.create(preferenceRequest);
        ultimoInitPoint = preference.getInitPoint();

        return ordenGuardada;
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
                case "approved" -> {
                    orden.setEstadoOrden(EstadoOrden.PAGADO.toString());

                    // 🔥 Obtener usuario y limpiar carrito
                    if (orden.getUsuario() != null) {
                        Long usuarioId = orden.getUsuario().getId();
                        detalleOrdenService.deleteByUsuarioId(usuarioId);
                    }
                }
                case "pending" -> orden.setEstadoOrden(EstadoOrden.PENDIENTE.toString());
                case "rejected" -> orden.setEstadoOrden(EstadoOrden.CANCELADO.toString());
                default -> orden.setEstadoOrden(EstadoOrden.PENDIENTE.toString());
            }

            ordenCompraRepository.save(orden);
        }
    }

    public PreferenceRequest construirPreferencia(OrdenCompra orden) {
        List<PreferenceItemRequest> items = new ArrayList<>();

        for (var detalle : orden.getDetalles()) {
            // Validar datos mínimos
            if (detalle.getCantidad() <= 0 || detalle.getPrecio() <= 0) {
                throw new IllegalArgumentException("La cantidad y el precio deben ser mayores a cero.");
            }

            //🧠 Cargar el productoTalle desde la BD usando su ID
            Long ptId = detalle.getProductoTalle().getId();
           var productoTalle = productoTalleRepository.findById(ptId)
                    .orElseThrow(() -> new IllegalArgumentException("ProductoTalle no encontrado con ID: " + ptId));

            var producto = productoTalle.getProducto();

            if (producto.getNombre() == null || producto.getDescripcion() == null) {
                throw new IllegalArgumentException("El producto debe tener nombre y descripción completos.");
            }

            // Agregar ítem a la preferencia
            items.add(PreferenceItemRequest.builder()
                    .id(productoTalle.getId().toString())
                    .title(producto.getNombre())
                    .description(producto.getDescripcion())
                    .unitPrice(BigDecimal.valueOf(detalle.getPrecio()))
                    .quantity(detalle.getCantidad())
                    .currencyId("ARS")
                    .build());
        }

        // URLs de redirección post-pago
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("https://youtu.be/dQw4w9WgXcQ?si=-Axp2WQ3zUkxCQjc")
                .failure("https://www.youtube.com/watch?v=lOg-0rEkWjw")
                .pending("https://www.youtube.com/watch?v=lOg-0rEkWjw")
                .build();



        // Construcción final de la preferencia
        return PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .autoReturn("approved")
                .externalReference(String.valueOf(orden.getId()))
                .notificationUrl("https://2ec8-38-51-31-185.ngrok-free.app/api/pagos/webhook")
                .build();
    }


}