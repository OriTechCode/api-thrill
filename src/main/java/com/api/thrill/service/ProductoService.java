package com.api.thrill.service;

import com.api.thrill.dto.ProductoDTO;
import com.api.thrill.entity.Producto;

import java.util.List;

public interface ProductoService extends BaseService<Producto, Long> {
    List<Producto> findByNombre(String nombre);
    List<Producto> findByMarca(String marca);

    List<Producto> findByTipo(String nombreTipo);

    // filtro por categoria , talle o categoria y talle
    List<Producto> findByCategoriaAndTalle(String nombreCategoria, String talle);

    Producto crearProductoDesdeDTO(ProductoDTO dto);
    Producto actualizarProductoDesdeDTO(Long id, ProductoDTO dto);

}