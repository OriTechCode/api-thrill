package com.api.thrill.service.impl;

import com.api.thrill.entity.OrdenCompra;
import com.api.thrill.repository.OrdenCompraRepository;
import com.api.thrill.service.OrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenCompraServiceImpl implements OrdenCompraService {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Override
    public List<OrdenCompra> findAll() {
        return ordenCompraRepository.findAll();
    }

    @Override
    public Optional<OrdenCompra> findById(Long id) {
        return ordenCompraRepository.findById(id);
    }

    @Override
    public OrdenCompra save(OrdenCompra ordenCompra) {
        return ordenCompraRepository.save(ordenCompra);
    }

    @Override
    public void deleteById(Long id) {
        ordenCompraRepository.deleteById(id);
    }

    @Override
    public OrdenCompra update(Long id, OrdenCompra ordenCompra) {
        if (ordenCompraRepository.existsById(id)) {
            ordenCompra.setId(id);
            return ordenCompraRepository.save(ordenCompra);
        }
        throw new RuntimeException("Orden de compra no encontrada con id: " + id);
    }
}