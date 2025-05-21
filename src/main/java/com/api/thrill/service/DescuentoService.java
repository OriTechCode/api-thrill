package com.api.thrill.service;

import com.api.thrill.entity.Descuento;
import java.util.List;

public interface DescuentoService {
    List<Descuento> findAll();
    Descuento findById(Long id);
    List<Descuento> findDescuentosVigentes();
    Descuento save(Descuento descuento);
    Descuento update(Long id, Descuento descuento);
    void delete(Long id);
}