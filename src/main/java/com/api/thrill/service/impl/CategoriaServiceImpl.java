package com.api.thrill.service.impl;

import com.api.thrill.entity.Categoria;
import com.api.thrill.repository.CategoriaRepository;
import com.api.thrill.service.CategoriaService;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl extends BaseServiceImpl<Categoria, Long> implements CategoriaService {

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        super(categoriaRepository); // Pasamos el repositorio al constructor de BaseServiceImpl
    }
}