package com.api.thrill.service;

import com.api.thrill.entity.OrdenCompra;
import com.api.thrill.entity.enums.EstadoOrden;
import com.api.thrill.repository.OrdenCompraRepository;
import com.api.thrill.repository.ProductoTalleRepository;
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
        orden.setMetodoPago("Mercado Pago");
        // Guardamos la orden inicialmente
        OrdenCompra ordenGuardada = ordenCompraRepository.save(orden);

        // Validamos si la orden tiene detalles
        if (orden.getDetalles() != null && !orden.getDetalles().isEmpty()) {
            // Evitamos duplicados creando una lista temporal
            List<DetalleOrden> detallesUnicos = new ArrayList<>();

            for (DetalleOrden detalle : orden.getDetalles()) {
                if (detalle.getId() == null) {
                    detalle.setOrden(ordenGuardada); // Asociar el detalle a la orden
                    DetalleOrden detalleGuardado = detalleOrdenService.save(detalle); // Guardar el detalle
                    detallesUnicos.add(detalleGuardado);
                } else {
                    DetalleOrden detalleActualizado = detalleOrdenService.update(detalle.getId(), detalle);
                    detallesUnicos.add(detalleActualizado);
                }
            }

            // Asignamos la lista de detalles única a la orden
            ordenGuardada.setDetalles(detallesUnicos);
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

                    if (orden.getDetalles() != null) {
                        for (DetalleOrden detalle : orden.getDetalles()) {
                            var productoTalle = detalle.getProductoTalle();
                            if (productoTalle == null) continue;

                            var pt = productoTalleRepository.findById(productoTalle.getId())
                                    .orElseThrow(() -> new Exception("ProductoTalle no encontrado con ID: " + productoTalle.getId()));

                            int nuevoStock = pt.getStock() - detalle.getCantidad();

                            // Si el stock ya es cero o menor que la cantidad solicitada, lo dejamos en 0 sin lanzar error
                            if (nuevoStock < 0) {
                                nuevoStock = 0; // Alternativa: lanzar excepción si preferís evitar esto
                            }

                            pt.setStock(nuevoStock);
                            productoTalleRepository.save(pt);
                        }
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

            // Cargar productoTalle desde la base de datos usando su ID
            Long ptId = detalle.getProductoTalle().getId();
            var productoTalle = productoTalleRepository.findById(ptId)
                    .orElseThrow(() -> new IllegalArgumentException("ProductoTalle no encontrado con ID: " + ptId));

            var producto = productoTalle.getProducto();

            if (producto.getNombre() == null || producto.getDescripcion() == null) {
                throw new IllegalArgumentException("El producto debe tener nombre y descripción completos.");
            }

            // Agregar ítem válido a la preferencia
            items.add(PreferenceItemRequest.builder()
                    .id(productoTalle.getId().toString())
                    .title(producto.getNombre())
                    .description(producto.getDescripcion())
                    .unitPrice(BigDecimal.valueOf(detalle.getPrecio())) // No modificar precio aquí
                    .quantity(detalle.getCantidad())
                    .currencyId("ARS")
                    .build());
        }
        // Solo si se envió un costo de envío
        if (orden.getCostoEnvio() != null && orden.getCostoEnvio().compareTo(BigDecimal.ZERO) > 0) {
            items.add(PreferenceItemRequest.builder()
                    .title("Costo de Envío")
                    .description("Costo según ubicación o tipo de envío")
                    .unitPrice(orden.getCostoEnvio())
                    .quantity(1)
                    .currencyId("ARS")
                    .build());
        }

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("https://thrillweb.netlify.app/carrito?message=pagado")
                .failure("https://thrillweb.netlify.app/carrito?message=fallido")
                .pending("")
                .build();

        return PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .autoReturn("approved")
                .externalReference(String.valueOf(orden.getId()))
                .notificationUrl("https://api-thrill-production-85ac.up.railway.app/api/pagos/webhook")
                .build();
    }


    public List<OrdenCompra> findByUsuarioId(Long id) {
        return ordenCompraRepository.findByUsuarioIdAndEliminadoFalse(id);
    }


    public void deleteById(Long id) throws Exception {
        OrdenCompra orden = ordenCompraRepository.findById(id)
                .orElseThrow(() -> new Exception("Orden no encontrada con ID: " + id));

        orden.setEliminado(true);
        ordenCompraRepository.save(orden);
    }
    public List<OrdenCompra> findDeleted() {
        return ordenCompraRepository.findByEliminadoTrue();
    }



}
