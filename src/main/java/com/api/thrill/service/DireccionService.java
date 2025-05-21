package com.api.thrill.service;

import com.api.thrill.entity.Direccion;
import java.util.List;

public interface DireccionService {
    List<Direccion> findAll();
    Direccion findById(Long id);
    List<Direccion> findByUsuarioId(Long usuarioId);
    Direccion save(Direccion direccion);
    Direccion update(Long id, Direccion direccion);
    void delete(Long id);
}