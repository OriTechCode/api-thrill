package com.api.thrill.service.impl;

import com.api.thrill.entity.Tipo;
import com.api.thrill.repository.TipoRepository;
import com.api.thrill.service.TipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TipoServiceImpl implements TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    @Override
    public List<Tipo> findAll() {
        return tipoRepository.findAll();
    }

    @Override
    public Tipo findById(Long id) {
        return tipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));
    }

    @Override
    public Tipo save(Tipo tipo) {
        return tipoRepository.save(tipo);
    }

    @Override
    public Tipo update(Long id, Tipo tipo) {
        Tipo tipoExistente = findById(id);
        tipoExistente.setNombre(tipo.getNombre());
        return tipoRepository.save(tipoExistente);
    }

    @Override
    public void delete(Long id) {
        Tipo tipo = findById(id);
        tipo.setEliminado(true);
        tipoRepository.save(tipo);
    }
}