package com.api.thrill.service.impl;

import com.api.thrill.entity.Direccion;
import com.api.thrill.repository.DireccionRepository;
import com.api.thrill.service.DireccionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DireccionServiceImpl extends BaseServiceImpl<Direccion, Long> implements DireccionService {

    private final DireccionRepository direccionRepository; // Inyectamos el repositorio

    public DireccionServiceImpl(DireccionRepository direccionRepository) {
        super(direccionRepository);
        this.direccionRepository = direccionRepository;
    }

    @Override
    public List<Direccion> findByUsuarioId(Long usuarioId) {

        return direccionRepository.findByUsuarioId(usuarioId);
    }
}