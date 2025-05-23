package com.api.thrill.service;

import com.api.thrill.entity.Producto;

import java.util.List;

public interface ProductoService extends BaseService<Producto, Long> {
    List<Producto> findByNombre(String nombre); // Búsqueda por nombre
    List<Producto> findByMarca(String marca);   // Búsqueda por marca


    //  buscar por el nombre de subcategoría
    List<Producto> findBySubcategoriaNombre(String nombreSubcategoria);
}