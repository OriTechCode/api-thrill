package com.api.thrill.repository;

import com.api.thrill.entity.OrdenCompra;
import com.api.thrill.entity.enums.EstadoOrden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {
    List<OrdenCompra> findAllByUsuarioId(Long usuarioId);

    @Override
    Optional<OrdenCompra> findById(Long aLong);

    List<OrdenCompra> findByEstadoOrden(EstadoOrden estadoOrden); // Corregido aquí
}