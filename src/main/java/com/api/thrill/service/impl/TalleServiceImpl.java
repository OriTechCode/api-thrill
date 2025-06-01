package com.api.thrill.service.impl;

import com.api.thrill.entity.Talle;
import com.api.thrill.repository.TalleRepository;
import com.api.thrill.service.TalleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TalleServiceImpl extends BaseServiceImpl<Talle, Long> implements TalleService {

    private final TalleRepository talleRepository;

    public TalleServiceImpl(TalleRepository talleRepository) {
        super(talleRepository);
        this.talleRepository = talleRepository;
    }

    @Override
    public List<Talle> findByTipoId(Long tipoId) {

        return talleRepository.findByTipoId(tipoId);
    }

    @Override
    public List<Talle> findByProductoId(Long productoId) {

        return talleRepository.findByProductoId(productoId);
    }
}