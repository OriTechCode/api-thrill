package com.api.thrill.controller;

import com.api.thrill.entity.Producto;
import com.api.thrill.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.save(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.update(id, producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLogico(@PathVariable Long id) {
        productoService.deleteLogic(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.findByNombre(nombre));
    }

    @GetMapping("/buscar/marca")
    public ResponseEntity<List<Producto>> buscarPorMarca(@RequestParam String marca) {
        return ResponseEntity.ok(productoService.findByMarca(marca));
    }
}