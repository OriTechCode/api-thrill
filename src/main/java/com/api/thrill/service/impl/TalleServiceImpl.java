package com.api.thrill.service.impl;

import com.api.thrill.entity.Talle;
import com.api.thrill.repository.TalleRepository;
import com.api.thrill.service.TalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TalleServiceImpl implements TalleService {

    @Autowired
    private TalleRepository talleRepository;

    @Override
    public List<Talle> findAll() {
        return talleRepository.findAll();
    }

    @Override
    public Talle findById(Long id) {
        return talleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Talle no encontrado"));
    }

    @Override
    public List<Talle> findByTipoId(Long tipoId) {
        return talleRepository.findByTipoId(tipoId);
    }

    @Override
    public List<Talle> findByProductoId(Long productoId) {
        return talleRepository.findByProductoId(productoId);
    }

    @Override
    public Talle save(Talle talle) {
        return talleRepository.save(talle);
    }

    @Override
    public Talle update(Long id, Talle talle) {
        Talle talleExistente = findById(id);
        talleExistente.setTalle(talle.getTalle());
        talleExistente.setProducto(talle.getProducto());
        talleExistente.setTipo(talle.getTipo());
        return talleRepository.save(talleExistente);
    }

    @Override
    public void delete(Long id) {
        Talle talle = findById(id);
        talle.setEliminado(true);
        talleRepository.save(talle);
    }
}