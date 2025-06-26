package com.api.thrill.service.impl;

import com.api.thrill.dto.ProductoDTO;
import com.api.thrill.entity.Categoria;
import com.api.thrill.entity.Imagen;
import com.api.thrill.entity.Producto;
import com.api.thrill.repository.CategoriaRepository;
import com.api.thrill.repository.ProductoRepository;
import com.api.thrill.service.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        // Guardar primero el producto
        Producto productoGuardado = productoRepository.save(producto);

        // Procesar y vincular las imágenes si existen
        if (dto.getImagenes() != null && !dto.getImagenes().isEmpty()) {
            List<Imagen> imagenes = dto.getImagenes().stream()
                    .map(urlImagen -> {
                        Imagen imagen = new Imagen();
                        imagen.setUrl(urlImagen); // Usar el string directamente
                        imagen.setProducto(productoGuardado);
                        return imagen;
                    })
                    .collect(Collectors.toList());

            productoGuardado.setImagenes(imagenes);
            return productoRepository.save(productoGuardado);
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