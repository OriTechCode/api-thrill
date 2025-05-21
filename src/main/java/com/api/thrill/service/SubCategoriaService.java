package com.api.thrill.service;

import com.api.thrill.entity.SubCategoria;
import java.util.List;

public interface SubCategoriaService {
    List<SubCategoria> findAll();
    SubCategoria findById(Long id);
    List<SubCategoria> findByCategoriaId(Long categoriaId);
    SubCategoria save(SubCategoria subCategoria);
    SubCategoria update(Long id, SubCategoria subCategoria);
    void delete(Long id);
}