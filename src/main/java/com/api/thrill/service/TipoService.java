package com.api.thrill.service;

import com.api.thrill.entity.Tipo;
import java.util.List;

public interface TipoService {
    List<Tipo> findAll();
    Tipo findById(Long id);
    Tipo save(Tipo tipo);
    Tipo update(Long id, Tipo tipo);
    void delete(Long id);
}