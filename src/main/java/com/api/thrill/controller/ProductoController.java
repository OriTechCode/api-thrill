package com.api.thrill.controller;

import com.api.thrill.dto.ProductoDTO;
import com.api.thrill.entity.Producto;
import com.api.thrill.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductoController extends BaseController<Producto, Long> {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        super(productoService);
        this.productoService = productoService;
    }

    // Sobreescribir el método create de BaseController para evitar el conflicto
    @Override
    @PostMapping("/base")
    public ResponseEntity<Producto> create(@RequestBody Producto entity) {
        return super.create(entity);
    }
    // Sobreescribir el método create de BaseController para evitar el conflicto
    @Override
    @PutMapping("/base")
    public ResponseEntity<Producto> update( @PathVariable Long id, @RequestBody Producto entity) {
        return super.update(id , entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        try {
            Producto actualizado = productoService.actualizarProductoDesdeDTO(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ProductoDTO productoDTO) {
        try {
            Producto producto = productoService.crearProductoDesdeDTO(productoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(producto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.findByNombre(nombre));
    }

    @GetMapping("/buscar/marca")
    public ResponseEntity<List<Producto>> buscarPorMarca(@RequestParam String marca) {
        return ResponseEntity.ok(productoService.findByMarca(marca));
    }



    @GetMapping("/buscar/tipo")
    public ResponseEntity<List<Producto>> buscarPorTipo(@RequestParam String nombreTipo) {
        return ResponseEntity.ok(productoService.findByTipo(nombreTipo));
    }

    // Nuevo endpoint para filtrar por categoría y/o talle
    @GetMapping("/buscar/categoria-talle")
    public ResponseEntity<List<Producto>> buscarPorCategoriaYTalle(
            @RequestParam(required = false) String nombreCategoria,
            @RequestParam(required = false) String talle) {
        return ResponseEntity.ok(productoService.findByCategoriaAndTalle(nombreCategoria, talle));
    }
}