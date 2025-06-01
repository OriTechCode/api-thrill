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
        super(subCategoriaRepository);
        this.subCategoriaRepository = subCategoriaRepository;
    }

    @Override
    public List<SubCategoria> findByCategoriaId(Long categoriaId) {

        return subCategoriaRepository.findByCategoriaId(categoriaId);
    }
}