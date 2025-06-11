package com.api.thrill.service;

import com.api.thrill.entity.DetalleOrden;
import java.util.List;
import java.util.Optional;

public interface DetalleOrdenService {
    List<DetalleOrden> findAll();
    Optional<DetalleOrden> findById(Long id);
    DetalleOrden save(DetalleOrden detalleOrden);
    void deleteById(Long id);
    DetalleOrden update(Long id, DetalleOrden detalleOrden);

    // Nuevo método
    void deleteByUsuarioId(Long usuarioId);
}