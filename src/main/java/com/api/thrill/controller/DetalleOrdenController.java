package com.api.thrill.controller;

import com.api.thrill.entity.DetalleOrden;
import com.api.thrill.service.DetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity.ok(detalleOrdenService.save(detalleOrden));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleOrden> update(@PathVariable Long id, @RequestBody DetalleOrden detalleOrden) {
        return ResponseEntity.ok(detalleOrdenService.update(id, detalleOrden));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        detalleOrdenService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}