package com.api.thrill.controller;

import com.api.thrill.entity.DetalleOrden;
import com.api.thrill.service.DetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles-orden")
public class DetalleOrdenController {

    @Autowired
    private DetalleOrdenService detalleOrdenService;

    @GetMapping
    public ResponseEntity<List<DetalleOrden>> getAll() {
        return ResponseEntity.ok(detalleOrdenService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleOrden> getById(@PathVariable Long id) {
        return detalleOrdenService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DetalleOrden> create(@RequestBody DetalleOrden detalleOrden) {
        try {
            DetalleOrden savedDetalle = detalleOrdenService.save(detalleOrden);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDetalle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleOrden> update(@PathVariable Long id, @RequestBody DetalleOrden detalleOrden) {
        try {
            DetalleOrden updatedDetalle = detalleOrdenService.update(id, detalleOrden);
            return ResponseEntity.status(HttpStatus.OK).body(updatedDetalle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            detalleOrdenService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Detalle de Orden no encontrado.");
        }
    }


    @DeleteMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> deleteByUsuarioId(@PathVariable Long usuarioId) {
        try {
            detalleOrdenService.deleteByUsuarioId(usuarioId);
            return ResponseEntity.ok("Todos los detalles del usuario con ID " + usuarioId + " han sido marcados como eliminados.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("No se pudieron eliminar los detalles: " + e.getMessage());
        }
    }


}