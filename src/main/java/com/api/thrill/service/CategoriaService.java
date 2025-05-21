package com.api.thrill.service;

import com.api.thrill.entity.Categoria;
import java.util.List;

public interface CategoriaService {
    List<Categoria> findAll();
    Categoria findById(Long id);
    Categoria save(Categoria categoria);
    Categoria update(Long id, Categoria categoria);
    void delete(Long id);
}