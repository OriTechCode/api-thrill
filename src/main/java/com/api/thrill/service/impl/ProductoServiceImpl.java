package com.api.thrill.service.impl;

import com.api.thrill.dto.ProductoDTO;
import com.api.thrill.entity.Categoria;
import com.api.thrill.entity.Producto;
import com.api.thrill.repository.CategoriaRepository;
import com.api.thrill.repository.ProductoRepository;
import com.api.thrill.service.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl extends BaseServiceImpl<Producto, Long> implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        super(productoRepository);
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Producto crearProductoDesdeDTO(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setMarca(dto.getMarca());
        producto.setPrecio(dto.getPrecio());

        // Asociar las categorías directamente
        if (dto.getCategorias() != null && !dto.getCategorias().isEmpty()) {
            producto.setCategorias(dto.getCategorias());
        }


        return productoRepository.save(producto);
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