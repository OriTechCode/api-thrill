package com.api.thrill.repository;

import com.api.thrill.entity.ProductoTalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoTalleRepository extends JpaRepository<ProductoTalle, Long> {
    List<ProductoTalle> findByProductoId(Long productoId);

    List<ProductoTalle> findByTalleId(Long talleId);

    Optional<ProductoTalle> findByProductoIdAndTalleId(Long productoId, Long talleId);
}
