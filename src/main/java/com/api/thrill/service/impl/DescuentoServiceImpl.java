package com.api.thrill.service.impl;

import com.api.thrill.entity.Descuento;
import com.api.thrill.repository.DescuentoRepository;
import com.api.thrill.service.DescuentoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DescuentoServiceImpl extends BaseServiceImpl<Descuento, Long> implements DescuentoService {

    private final DescuentoRepository descuentoRepository;

    public DescuentoServiceImpl(DescuentoRepository descuentoRepository) {
        super(descuentoRepository);
        this.descuentoRepository = descuentoRepository;
    }

    @Override
    public List<Descuento> findDescuentosVigentes() {
        LocalDate now = LocalDate.now();
        return descuentoRepository.findByFechaFinGreaterThanEqualAndFechaInicioLessThanEqual(now, now);
    }

    @Override
    public Descuento save(Descuento descuento) {
        validarFechas(descuento);
        return super.save(descuento);
    }

    @Override
    public Descuento update(Long id, Descuento descuento) {
        Descuento descuentoExistente = super.findById(id)
                .orElseThrow(() -> new RuntimeException("Descuento no encontrado"));

        validarFechas(descuento);

        descuentoExistente.setFechaInicio(descuento.getFechaInicio());
        descuentoExistente.setFechaFin(descuento.getFechaFin());
        descuentoExistente.setPorcentajeDesc(descuento.getPorcentajeDesc());

        return super.save(descuentoExistente);
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