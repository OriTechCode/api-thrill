package com.api.thrill.repository;

import com.api.thrill.entity.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenRepository extends JpaRepository<Imagen, Long> {
    List<Imagen> findByProductoId(Long productoId);
}