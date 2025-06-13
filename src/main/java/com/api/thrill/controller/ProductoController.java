package com.api.thrill.controller;

import com.api.thrill.dto.ProductoDTO;
import com.api.thrill.entity.Imagen;
import com.api.thrill.entity.Producto;
import com.api.thrill.service.ImagenService;
import com.api.thrill.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController extends BaseController<Producto, Long> {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        super(productoService);
        this.productoService = productoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearProductoConImagenes(@RequestBody ProductoDTO request) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setMarca(request.getMarca());
        producto.setCategorias(request.getCategorias());

        List<Imagen> imagenes = request.getImagenesUrls().stream().map(url -> {
            Imagen img = new Imagen();
            img.setUrl(url);
            img.setProducto(producto); // importante para mantener la relación
            return img;
        }).toList();

        producto.setImagenes(imagenes);
        Producto productoGuardado = productoService.save(producto);

        return ResponseEntity.ok(productoGuardado);
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