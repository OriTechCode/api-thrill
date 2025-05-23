package com.api.thrill.service;

import com.api.thrill.entity.SubCategoria;

import java.util.List;

public interface SubCategoriaService extends BaseService<SubCategoria, Long> {
    List<SubCategoria> findByCategoriaId(Long categoriaId); // Método específico
}