package com.api.thrill.service.impl;

import com.api.thrill.entity.DetalleOrden;
import com.api.thrill.repository.DetalleOrdenRepository;
import com.api.thrill.service.DetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleOrdenServiceImpl implements DetalleOrdenService {

    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    @Override
    public List<DetalleOrden> findAll() {
        return detalleOrdenRepository.findAll();
    }

    @Override
    public Optional<DetalleOrden> findById(Long id) {
        return detalleOrdenRepository.findById(id);
    }

    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        return detalleOrdenRepository.save(detalleOrden);
    }

    @Override
    public void deleteById(Long id) {
        detalleOrdenRepository.deleteById(id);
    }

    @Override
    public DetalleOrden update(Long id, DetalleOrden detalleOrden) {
        if (detalleOrdenRepository.existsById(id)) {
            detalleOrden.setId(id);
            return detalleOrdenRepository.save(detalleOrden);
        }
        throw new RuntimeException("Detalle de orden no encontrado con id: " + id);
    }
}