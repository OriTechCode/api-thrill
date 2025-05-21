package com.api.thrill.controller;

import com.api.thrill.entity.OrdenCompra;
import com.api.thrill.service.OrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraService ordenCompraService;

    @GetMapping
    public ResponseEntity<List<OrdenCompra>> getAll() {
        return ResponseEntity.ok(ordenCompraService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompra> getById(@PathVariable Long id) {
        return ordenCompraService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrdenCompra> create(@RequestBody OrdenCompra ordenCompra) {
        return ResponseEntity.ok(ordenCompraService.save(ordenCompra));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenCompra> update(@PathVariable Long id, @RequestBody OrdenCompra ordenCompra) {
        return ResponseEntity.ok(ordenCompraService.update(id, ordenCompra));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ordenCompraService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}