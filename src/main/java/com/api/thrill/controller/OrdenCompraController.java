package com.api.thrill.controller;

import com.api.thrill.entity.OrdenCompra;
import com.api.thrill.service.OrdenCompraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
@CrossOrigin(origins = "*")
public class OrdenCompraController {

    private final OrdenCompraService ordenCompraService;

    public OrdenCompraController(OrdenCompraService ordenCompraService) {
        this.ordenCompraService = ordenCompraService;
    }

    @GetMapping
    public List<OrdenCompra> listar() {
        return ordenCompraService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompra> obtenerPorId(@PathVariable Long id) {
        return ordenCompraService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrdenCompra> crear(@RequestBody OrdenCompra ordenCompra) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenCompraService.guardar(ordenCompra));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenCompra> actualizar(@PathVariable Long id, @RequestBody OrdenCompra ordenCompra) {
        return ordenCompraService.buscarPorId(id)
                .map(o -> {
                    ordenCompra.setId(id);
                    return ResponseEntity.ok(ordenCompraService.guardar(ordenCompra));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (ordenCompraService.buscarPorId(id).isPresent()) {
            ordenCompraService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
