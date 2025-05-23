package com.api.thrill.service;

import com.api.thrill.entity.Direccion;

import java.util.List;

public interface DireccionService extends BaseService<Direccion, Long> {
    List<Direccion> findByUsuarioId(Long usuarioId); // Método específico que no se generaliza
}