package com.api.thrill.repository;

import com.api.thrill.entity.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE DetalleOrden d SET d.eliminado = true WHERE d.usuario.id = :usuarioId")
    void logicalDeleteByUsuarioId(Long usuarioId); // Eliminación lógica por usuario

    @Query("SELECT d FROM DetalleOrden d WHERE d.usuario.id = :usuarioId AND d.eliminado = false")
    List<DetalleOrden> findNonDeletedByUsuarioId(Long usuarioId); // Consultar detalles no eliminados
}