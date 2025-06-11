package com.api.thrill.service.impl;

import com.api.thrill.entity.DetalleOrden;
import com.api.thrill.repository.DetalleOrdenRepository;
import com.api.thrill.service.DetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleOrdenServiceImpl implements DetalleOrdenService {

    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    @Override
    public List<DetalleOrden> findAll() {
        // Solo devolver detalles no eliminados
        return detalleOrdenRepository.findAll().stream().filter(detalle -> !detalle.getEliminado()).toList();
    }

    @Override
    public Optional<DetalleOrden> findById(Long id) {
        return detalleOrdenRepository.findById(id)
                .filter(detalle -> !detalle.getEliminado()); // Opcionalmente, excluimos los eliminados
    }

    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        if (detalleOrden.getOrden() != null) {
            detalleOrden.getOrden().getDetalles().add(detalleOrden);
        }
        detalleOrden.setEliminado(false); // Asegurarnos que al guardar nuevos detalles, no estén eliminados
        return detalleOrdenRepository.save(detalleOrden);
    }

    @Override
    public void deleteById(Long id) {
        Optional<DetalleOrden> detalle = detalleOrdenRepository.findById(id);
        if (detalle.isEmpty() || detalle.get().getEliminado()) {
            throw new RuntimeException("Detalle de orden no encontrado o ya eliminado con ID: " + id);
        }
        DetalleOrden detalleOrden = detalle.get();
        detalleOrden.setEliminado(true); // Actualizar el estado eliminado
        detalleOrdenRepository.save(detalleOrden); // Guardar estado actualizado
    }

    @Override
    public DetalleOrden update(Long id, DetalleOrden detalleOrden) {
        if (!detalleOrdenRepository.existsById(id)) {
            throw new RuntimeException("Detalle de orden no encontrado con ID: " + id);
        }
        detalleOrden.setId(id);
        return detalleOrdenRepository.save(detalleOrden);
    }

    @Override
    public void deleteByUsuarioId(Long usuarioId) {
        detalleOrdenRepository.logicalDeleteByUsuarioId(usuarioId);
    }
}