package com.api.thrill.service;

import com.api.thrill.entity.DetalleOrden;
import java.util.List;
import java.util.Optional;

public interface DetalleOrdenService extends BaseService<DetalleOrden, Long>{

    // Nuevo método
    void deleteByUsuarioId(Long usuarioId);
}