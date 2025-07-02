package com.api.thrill.repository;

import com.api.thrill.entity.OrdenCompra;
import com.api.thrill.entity.enums.EstadoOrden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {


    List<OrdenCompra> findByUsuarioIdAndEliminadoFalse(Long usuarioId);

    List<OrdenCompra> findByEstadoOrden(EstadoOrden estadoOrden); // Corregido aquí
}