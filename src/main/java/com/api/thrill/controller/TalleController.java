package com.api.thrill.controller;

import com.api.thrill.entity.Talle;
import com.api.thrill.service.TalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/talles")
public class TalleController {

    @Autowired
    private TalleService talleService;

    @GetMapping("")
    public ResponseEntity<List<Talle>> findAll() {
        return ResponseEntity.ok(talleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Talle> findById(@PathVariable Long id) {
        return ResponseEntity.ok(talleService.findById(id));
    }

    @GetMapping("/tipo/{tipoId}")
    public ResponseEntity<List<Talle>> findByTipoId(@PathVariable Long tipoId) {
        return ResponseEntity.ok(talleService.findByTipoId(tipoId));
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<Talle>> findByProductoId(@PathVariable Long productoId) {
        return ResponseEntity.ok(talleService.findByProductoId(productoId));
    }

    @PostMapping("")
    public ResponseEntity<Talle> save(@RequestBody Talle talle) {
        return new ResponseEntity<>(talleService.save(talle), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Talle> update(@PathVariable Long id, @RequestBody Talle talle) {
        return ResponseEntity.ok(talleService.update(id, talle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        talleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}