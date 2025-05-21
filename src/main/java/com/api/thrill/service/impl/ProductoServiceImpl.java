package com.api.thrill.service.impl;

import com.api.thrill.entity.Producto;
import com.api.thrill.repository.ProductoRepository;
import com.api.thrill.service.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public Producto findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto update(Long id, Producto producto) {
        Producto actual = findById(id);
        producto.setId(actual.getId());
        return productoRepository.save(producto);
    }

    @Override
    public void deleteLogic(Long id) {
        Producto producto = findById(id);
        producto.setEliminado(true);
        productoRepository.save(producto);
    }

    @Override
    public List<Producto> findByNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Producto> findByMarca(String marca) {
        return productoRepository.findByMarcaIgnoreCase(marca);
    }
}