package com.api.thrill.service.impl;

import com.api.thrill.entity.Direccion;
import com.api.thrill.repository.DireccionRepository;
import com.api.thrill.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DireccionServiceImpl implements DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    @Override
    public List<Direccion> findAll() {
        return direccionRepository.findAll();
    }

    @Override
    public Direccion findById(Long id) {
        return direccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));
    }

    @Override
    public List<Direccion> findByUsuarioId(Long usuarioId) {
        return direccionRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public Direccion save(Direccion direccion) {
        return direccionRepository.save(direccion);
    }

    @Override
    public Direccion update(Long id, Direccion direccion) {
        Direccion direccionExistente = findById(id);
        direccionExistente.setCalle(direccion.getCalle());
        direccionExistente.setLocalidad(direccion.getLocalidad());
        direccionExistente.setCodpostal(direccion.getCodpostal());
        direccionExistente.setUsuario(direccion.getUsuario());
        return direccionRepository.save(direccionExistente);
    }

    @Override
    public void delete(Long id) {
        Direccion direccion = findById(id);
        direccion.setEliminado(true);
        direccionRepository.save(direccion);
    }
}