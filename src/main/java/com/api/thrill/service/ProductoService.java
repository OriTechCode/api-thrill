package com.api.thrill.service;

import com.api.thrill.entity.Producto;

import java.util.List;

public interface ProductoService extends BaseService<Producto, Long> {
    List<Producto> findByNombre(String nombre);
    List<Producto> findByMarca(String marca);



    List<Producto> findBySubcategoriaNombre(String nombreSubcategoria);
}