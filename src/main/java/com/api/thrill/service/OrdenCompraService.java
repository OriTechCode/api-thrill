package com.api.thrill.service;

import com.api.thrill.entity.OrdenCompra;
import com.api.thrill.repository.OrdenCompraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenCompraService {
    private final OrdenCompraRepository ordenCompraRepository;

    public OrdenCompraService(OrdenCompraRepository ordenCompraRepository) {
        this.ordenCompraRepository = ordenCompraRepository;
    }

    public List<OrdenCompra> listarTodas() {
        return ordenCompraRepository.findAll();
    }

    public Optional<OrdenCompra> buscarPorId(Long id) {
        return ordenCompraRepository.findById(id);
    }

    public OrdenCompra guardar(OrdenCompra ordenCompra) {
        return ordenCompraRepository.save(ordenCompra);
    }

    public void eliminar(Long id) {
        ordenCompraRepository.deleteById(id);
    }
}
