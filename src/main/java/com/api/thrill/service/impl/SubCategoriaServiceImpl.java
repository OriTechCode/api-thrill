package com.api.thrill.service.impl;

import com.api.thrill.entity.SubCategoria;
import com.api.thrill.repository.SubCategoriaRepository;
import com.api.thrill.service.SubCategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SubCategoriaServiceImpl implements SubCategoriaService {

    @Autowired
    private SubCategoriaRepository subCategoriaRepository;

    @Override
    public List<SubCategoria> findAll() {
        return subCategoriaRepository.findAll();
    }

    @Override
    public SubCategoria findById(Long id) {
        return subCategoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubCategoria no encontrada"));
    }

    @Override
    public List<SubCategoria> findByCategoriaId(Long categoriaId) {
        return subCategoriaRepository.findByCategoriaId(categoriaId);
    }

    @Override
    public SubCategoria save(SubCategoria subCategoria) {
        return subCategoriaRepository.save(subCategoria);
    }

    @Override
    public SubCategoria update(Long id, SubCategoria subCategoria) {
        SubCategoria subCategoriaExistente = findById(id);
        subCategoriaExistente.setNombre(subCategoria.getNombre());
        subCategoriaExistente.setCategoria(subCategoria.getCategoria());
        return subCategoriaRepository.save(subCategoriaExistente);
    }

    @Override
    public void delete(Long id) {
        SubCategoria subCategoria = findById(id);
        subCategoria.setEliminado(true);
        subCategoriaRepository.save(subCategoria);
    }
}