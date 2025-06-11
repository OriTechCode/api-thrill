package com.api.thrill.repository;

import com.api.thrill.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByMarcaIgnoreCase(String marca);

    List<Producto> findByTipoNombreIgnoreCase(String nombreTipo);

    // Método personalizado para filtrar por categoría y/o por talle
    @Query("SELECT p FROM Producto p \n" +
            "LEFT JOIN p.categorias c \n" +
            "LEFT JOIN p.productoTalles pt \n" +
            "LEFT JOIN pt.talle t \n" +
            "WHERE (:nombreCategoria IS NULL OR c.nombre = :nombreCategoria) \n" +
            "AND (:talle IS NULL OR t.talle = :talle)\n")
    List<Producto> findByCategoriaAndTalle(
            @org.springframework.lang.Nullable String nombreCategoria,
            @org.springframework.lang.Nullable String talle);
}