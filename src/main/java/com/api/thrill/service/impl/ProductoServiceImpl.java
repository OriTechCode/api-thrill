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
public abstract class ProductoServiceImpl extends BaseServiceImpl<Producto, Long> implements ProductoService {

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

        // Verificar si el DTO incluye IDs de categorías antes de buscarlas
        if (dto.getCategoriaIds() != null && !dto.getCategoriaIds().isEmpty()) {
            List<Categoria> categorias = categoriaRepository.findAllById(dto.getCategoriaIds());
            if (categorias.isEmpty()) {
                throw new RuntimeException("Una o más categorías no se encontrarón");
            }
            producto.setCategorias(categorias);
        }

        return productoRepository.save(producto);
    }
}