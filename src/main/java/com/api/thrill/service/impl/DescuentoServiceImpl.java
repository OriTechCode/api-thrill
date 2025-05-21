package com.api.thrill.service.impl;

import com.api.thrill.entity.Descuento;
import com.api.thrill.repository.DescuentoRepository;
import com.api.thrill.service.DescuentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class DescuentoServiceImpl implements DescuentoService {

    @Autowired
    private DescuentoRepository descuentoRepository;

    @Override
    public List<Descuento> findAll() {
        return descuentoRepository.findAll();
    }

    @Override
    public Descuento findById(Long id) {
        return descuentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Descuento no encontrado"));
    }

    @Override
    public List<Descuento> findDescuentosVigentes() {
        LocalDate now = LocalDate.now();
        return descuentoRepository
                .findByFechaFinGreaterThanEqualAndFechaInicioLessThanEqual(now, now);
    }

    @Override
    public Descuento save(Descuento descuento) {
        validarFechas(descuento);
        return descuentoRepository.save(descuento);
    }

    @Override
    public Descuento update(Long id, Descuento descuento) {
        Descuento descuentoExistente = findById(id);
        validarFechas(descuento);

        descuentoExistente.setFechaInicio(descuento.getFechaInicio());
        descuentoExistente.setFechaFin(descuento.getFechaFin());
        descuentoExistente.setPorcentajeDesc(descuento.getPorcentajeDesc());

        return descuentoRepository.save(descuentoExistente);
    }

    @Override
    public void delete(Long id) {
        Descuento descuento = findById(id);
        descuento.setEliminado(true);
        descuentoRepository.save(descuento);
    }

    private void validarFechas(Descuento descuento) {
        if (descuento.getFechaInicio().isAfter(descuento.getFechaFin())) {
            throw new RuntimeException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
        if (descuento.getPorcentajeDesc() <= 0 || descuento.getPorcentajeDesc() > 100) {
            throw new RuntimeException("El porcentaje de descuento debe estar entre 0 y 100");
        }
    }
}