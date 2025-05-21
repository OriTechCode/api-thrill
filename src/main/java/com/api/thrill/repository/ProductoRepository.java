package com.api.thrill.repository;

import com.api.thrill.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByMarcaIgnoreCase(String marca);
    List<Producto> findBySubcategoriaNombreIgnoreCase(String nombreSubcategoria);
}