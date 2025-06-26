package com.api.thrill.service.impl;

import com.api.thrill.dto.ProductoDTO;
import com.api.thrill.entity.Categoria;
import com.api.thrill.entity.Imagen;
import com.api.thrill.entity.Producto;
import com.api.thrill.entity.Tipo;
import com.api.thrill.repository.CategoriaRepository;
import com.api.thrill.repository.ProductoRepository;
import com.api.thrill.repository.TipoRepository;
import com.api.thrill.service.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl extends BaseServiceImpl<Producto, Long> implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final TipoRepository tipoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, TipoRepository tipoRepository) {
        super(productoRepository);
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.tipoRepository = tipoRepository;
    }

@Override
public Producto crearProductoDesdeDTO(ProductoDTO dto) {
    try {
        System.out.println("DTO recibido: " + dto);
        
        // Crear producto básico
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setMarca(dto.getMarca());
        producto.setPrecio(dto.getPrecio());
        producto.setColor(dto.getColor());
        producto.setCantidad(0);
        
        // Asignar tipo
        if (dto.getTipoId() != null) {
            Tipo tipo = tipoRepository.findById(dto.getTipoId())
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado con ID: " + dto.getTipoId()));
            producto.setTipo(tipo);
        }
        
        // Asignar categorías
        if (dto.getCategoriaIds() != null && !dto.getCategoriaIds().isEmpty()) {
            List<Categoria> categorias = categoriaRepository.findAllById(dto.getCategoriaIds());
            if (categorias.size() != dto.getCategoriaIds().size()) {
                throw new RuntimeException("No se encontraron todas las categorías solicitadas");
            }
            producto.setCategorias(categorias);
        }
        
        // Guardar producto
        Producto productoGuardado = productoRepository.save(producto);
        
        // Procesar imágenes - MODIFICADO PARA EVITAR ERROR DE COLECCIÓN HUÉRFANA
        if (dto.getImagenes() != null && !dto.getImagenes().isEmpty()) {
            // NO crear una nueva lista, usar la que ya existe en el producto
            for (String urlImagen : dto.getImagenes()) {
                Imagen imagen = new Imagen();
                imagen.setUrl(urlImagen);
                imagen.setProducto(productoGuardado);
                productoGuardado.getImagenes().add(imagen); // Añadir a la lista existente
            }
            
            return productoRepository.save(productoGuardado);
        }
        
        return productoGuardado;
    } catch (Exception e) {
        System.err.println("Error al crear producto: " + e.getMessage());
        e.printStackTrace();
        throw e;
    }
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