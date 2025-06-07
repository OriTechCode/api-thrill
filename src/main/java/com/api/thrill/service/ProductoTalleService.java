package com.api.thrill.service;

import com.api.thrill.entity.ProductoTalle;

import java.util.List;
import java.util.Optional;

public interface ProductoTalleService extends BaseService<ProductoTalle, Long> {
    List<ProductoTalle> findByProductoId(Long productoId);
    List<ProductoTalle> findByTalleId(Long talleId);
    Optional<ProductoTalle> findByProductoIdAndTalleId(Long productoId, Long talleId);
    void updateStock(Long productoId, Long talleId, int nuevoStock);
    boolean verificarDisponibilidad(Long productoId, Long talleId, int cantidadSolicitada);
}