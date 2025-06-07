package com.api.thrill.service;

import com.api.thrill.entity.Talle;

import java.util.List;

public interface TalleService extends BaseService<Talle, Long> {
    List<Talle> findByTipoId(Long tipoId);
}