package com.api.thrill.controller;

import com.api.thrill.entity.Direccion;
import com.api.thrill.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/direcciones")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @GetMapping("")
    public ResponseEntity<List<Direccion>> findAll() {
        return ResponseEntity.ok(direccionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Direccion> findById(@PathVariable Long id) {
        return ResponseEntity.ok(direccionService.findById(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Direccion>> findByUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(direccionService.findByUsuarioId(usuarioId));
    }

    @PostMapping("")
    public ResponseEntity<Direccion> save(@RequestBody Direccion direccion) {
        return new ResponseEntity<>(direccionService.save(direccion), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Direccion> update(@PathVariable Long id, @RequestBody Direccion direccion) {
        return ResponseEntity.ok(direccionService.update(id, direccion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        direccionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}