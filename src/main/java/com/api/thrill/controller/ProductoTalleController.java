package com.api.thrill.controller;

import com.api.thrill.entity.ProductoTalle;
import com.api.thrill.service.ProductoTalleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto-talle")
public class ProductoTalleController extends BaseController<ProductoTalle, Long> {

    private final ProductoTalleService productoTalleService;

    public ProductoTalleController(ProductoTalleService productoTalleService) {
        super(productoTalleService);
        this.productoTalleService = productoTalleService;
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<ProductoTalle>> findByProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(productoTalleService.findByProductoId(productoId));
    }

    @GetMapping("/talle/{talleId}")
    public ResponseEntity<List<ProductoTalle>> findByTalle(@PathVariable Long talleId) {
        return ResponseEntity.ok(productoTalleService.findByTalleId(talleId));
    }
    @GetMapping("/verificar-stock")
    public ResponseEntity<Boolean> verificarStock(@RequestParam Long productoId, @RequestParam Long talleId, @RequestParam int cantidad) {
        boolean disponible = productoTalleService.verificarDisponibilidad(productoId, talleId, cantidad);
        return ResponseEntity.ok(disponible);
    }
    @GetMapping("/producto/{productoId}/talle/{talleId}")
    public ResponseEntity<ProductoTalle> getByProductoAndTalleId(@PathVariable Long productoId, @PathVariable Long talleId) {
        return productoTalleService.findByProductoIdAndTalleId(productoId, talleId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/stock")
    public ResponseEntity<Void> updateStock(@RequestParam Long productoId, @RequestParam Long talleId, @RequestParam int nuevoStock) {
        productoTalleService.updateStock(productoId, talleId, nuevoStock);
        return ResponseEntity.ok().build();
    }


}