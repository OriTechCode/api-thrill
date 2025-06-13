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
        // Establecer el flag 'eliminado' como false para nuevos detalles
        detalleOrden.setEliminado(false);
        
        // Asegurar que la relación con orden es correcta sin usar addDetalle
        // que es lo que estaba causando duplicados
        if (detalleOrden.getOrden() != null) {
            // No hacemos nada extra aquí, simplemente dejamos la relación establecida
        }
        
        // Guardar el detalle
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