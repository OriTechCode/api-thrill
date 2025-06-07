package com.api.thrill.service.impl;

import com.api.thrill.entity.ProductoTalle;
import com.api.thrill.repository.ProductoTalleRepository;
import com.api.thrill.service.ProductoTalleService;
import com.api.thrill.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoTalleServiceImpl extends BaseServiceImpl<ProductoTalle, Long> implements ProductoTalleService {

    private final ProductoTalleRepository productoTalleRepository;

    public ProductoTalleServiceImpl(ProductoTalleRepository productoTalleRepository) {
        super(productoTalleRepository);
        this.productoTalleRepository = productoTalleRepository;
    }

    @Override
    public List<ProductoTalle> findByProductoId(Long productoId) {
        return productoTalleRepository.findByProductoId(productoId);
    }

    @Override
    public List<ProductoTalle> findByTalleId(Long talleId) {
        return productoTalleRepository.findByTalleId(talleId);
    }

    @Override
    public Optional<ProductoTalle> findByProductoIdAndTalleId(Long productoId, Long talleId) {
        return productoTalleRepository.findByProductoIdAndTalleId(productoId, talleId);
    }

    @Override
    public void updateStock(Long productoId, Long talleId, int nuevoStock) {
        productoTalleRepository.findByProductoIdAndTalleId(productoId, talleId).ifPresent(pt -> {
            pt.setStock(nuevoStock);
            productoTalleRepository.save(pt);
        });
    }

    @Override
    public boolean verificarDisponibilidad(Long productoId, Long talleId, int cantidadSolicitada) {
        return productoTalleRepository.findByProductoIdAndTalleId(productoId, talleId)
                .map(pt -> pt.getStock() >= cantidadSolicitada)
                .orElse(false);
    }
}