package com.api.thrill.service.impl;

import com.api.thrill.entity.Producto;
import com.api.thrill.repository.ProductoRepository;
import com.api.thrill.service.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl extends BaseServiceImpl<Producto, Long> implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        super(productoRepository);
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> findByNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Producto> findByMarca(String marca) {
        return productoRepository.findByMarcaIgnoreCase(marca);
    }



    @Override
    public List<Producto> findByTipo(String nombreTipo) { 
        return productoRepository.findByTipoNombreIgnoreCase(nombreTipo);
    }

    @Override
    public List<Producto> findByCategoriaAndTalle(String nombreCategoria, String talle) {
        return productoRepository.findByCategoriaAndTalle(nombreCategoria, talle);
    }
}