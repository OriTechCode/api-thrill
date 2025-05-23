package com.api.thrill.service.impl;

import com.api.thrill.entity.SubCategoria;
import com.api.thrill.repository.SubCategoriaRepository;
import com.api.thrill.service.SubCategoriaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoriaServiceImpl extends BaseServiceImpl<SubCategoria, Long> implements SubCategoriaService {

    private final SubCategoriaRepository subCategoriaRepository;

    public SubCategoriaServiceImpl(SubCategoriaRepository subCategoriaRepository) {
        super(subCategoriaRepository); // Pasamos el repositorio genérico al constructor del BaseServiceImpl
        this.subCategoriaRepository = subCategoriaRepository;
    }

    @Override
    public List<SubCategoria> findByCategoriaId(Long categoriaId) {
        // Lógica específica que utiliza el repositorio para buscar por ID de categoría
        return subCategoriaRepository.findByCategoriaId(categoriaId);
    }
}