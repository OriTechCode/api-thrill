package com.api.thrill.service.impl;

import com.api.thrill.entity.Categoria;
import com.api.thrill.repository.CategoriaRepository;
import com.api.thrill.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria findById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    }

    @Override
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria update(Long id, Categoria categoria) {
        Categoria categoriaExistente = findById(id);
        categoriaExistente.setNombre(categoria.getNombre());
        return categoriaRepository.save(categoriaExistente);
    }

    @Override
    public void delete(Long id) {
        Categoria categoria = findById(id);
        categoria.setEliminado(true);
        categoriaRepository.save(categoria);
    }
}