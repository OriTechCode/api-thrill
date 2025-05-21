package com.api.thrill.repository;

import com.api.thrill.entity.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    List<Direccion> findByUsuarioId(Long usuarioId);
}
