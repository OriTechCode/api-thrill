package com.api.thrill.service;

import com.api.thrill.entity.Descuento;
import java.util.List;

public interface DescuentoService extends BaseService<Descuento, Long> {
    List<Descuento> findDescuentosVigentes(); // Método específico para descuentos vigentes
}