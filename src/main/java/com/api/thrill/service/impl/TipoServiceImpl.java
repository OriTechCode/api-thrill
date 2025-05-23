package com.api.thrill.service.impl;

import com.api.thrill.entity.Tipo;
import com.api.thrill.repository.TipoRepository;
import com.api.thrill.service.TipoService;
import org.springframework.stereotype.Service;

@Service
public class TipoServiceImpl extends BaseServiceImpl<Tipo, Long> implements TipoService {

    private final TipoRepository tipoRepository;

    public TipoServiceImpl(TipoRepository tipoRepository) {
        super(tipoRepository); // Pasamos TipoRepository al constructor de la clase base
        this.tipoRepository = tipoRepository;
    }
}