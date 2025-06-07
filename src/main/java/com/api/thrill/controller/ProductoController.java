package com.api.thrill.controller;

import com.api.thrill.entity.Producto;
import com.api.thrill.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController extends BaseController<Producto, Long> {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        super(productoService);
        this.productoService = productoService;
    }


    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.findByNombre(nombre));
    }

    @GetMapping("/buscar/marca")
    public ResponseEntity<List<Producto>> buscarPorMarca(@RequestParam String marca) {
        return ResponseEntity.ok(productoService.findByMarca(marca));
    }

    @GetMapping("/buscar/subcategoria")
    public ResponseEntity<List<Producto>> buscarPorSubcategoria(@RequestParam String nombreSubcategoria) {

        return ResponseEntity.ok(productoService.findBySubcategoriaNombre(nombreSubcategoria));
    }
}