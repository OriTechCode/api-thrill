package com.api.thrill.controller;

import com.api.thrill.entity.Descuento;
import com.api.thrill.service.DescuentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/descuentos")
public class DescuentoController {

    @Autowired
    private DescuentoService descuentoService;

    @GetMapping("")
    public ResponseEntity<List<Descuento>> findAll() {
        return ResponseEntity.ok(descuentoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Descuento> findById(@PathVariable Long id) {
        return ResponseEntity.ok(descuentoService.findById(id));
    }

    @GetMapping("/vigentes")
    public ResponseEntity<List<Descuento>> findDescuentosVigentes() {
        return ResponseEntity.ok(descuentoService.findDescuentosVigentes());
    }

    @PostMapping("")
    public ResponseEntity<Descuento> save(@RequestBody Descuento descuento) {
        return new ResponseEntity<>(descuentoService.save(descuento), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Descuento> update(@PathVariable Long id, @RequestBody Descuento descuento) {
        return ResponseEntity.ok(descuentoService.update(id, descuento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        descuentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}