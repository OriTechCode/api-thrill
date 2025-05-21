package com.api.thrill.service;

import com.api.thrill.entity.Talle;
import java.util.List;

public interface TalleService {
    List<Talle> findAll();
    Talle findById(Long id);
    List<Talle> findByTipoId(Long tipoId);
    List<Talle> findByProductoId(Long productoId);
    Talle save(Talle talle);
    Talle update(Long id, Talle talle);
    void delete(Long id);
}