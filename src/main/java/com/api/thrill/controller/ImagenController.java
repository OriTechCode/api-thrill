
package com.api.thrill.controller;

import com.api.thrill.entity.Imagen;
import com.api.thrill.service.ImagenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/imagenes")
@CrossOrigin(origins = "*")
public class ImagenController {
    private final ImagenService imagenService;

    public ImagenController(ImagenService imagenService) {
        this.imagenService = imagenService;
    }

    @PostMapping("/producto/{id}")
    public ResponseEntity<?> subirImagenProducto(@RequestParam("imagen") MultipartFile archivo,
                                                 @PathVariable Long id) {
        try {
            Imagen imagen = imagenService.subirImagenProducto(archivo, id);
            return ResponseEntity.ok()
                    .body(Map.of(
                            "mensaje", "Imagen subida con éxito",
                            "imagen", imagen
                    ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al procesar la imagen: " + e.getMessage()));
        }
    }

    @PostMapping("/usuario/{id}")
    public ResponseEntity<?> subirImagenUsuario(@RequestParam("imagen") MultipartFile archivo,
                                                @PathVariable Long id) {
        try {
            Imagen imagen = imagenService.subirImagenUsuario(archivo, id);
            return ResponseEntity.ok()
                    .body(Map.of(
                            "mensaje", "Imagen de perfil actualizada con éxito",
                            "imagen", imagen
                    ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al procesar la imagen: " + e.getMessage()));
        }
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<?> obtenerImagenesProducto(@PathVariable Long productoId) {
        try {
            List<Imagen> imagenes = imagenService.obtenerImagenesProducto(productoId);
            return ResponseEntity.ok(imagenes);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarImagen(@PathVariable Long id) {
        try {
            imagenService.eliminarImagen(id);
            return ResponseEntity.ok()
                    .body(Map.of("mensaje", "Imagen eliminada con éxito"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al eliminar la imagen: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarImagen(@PathVariable Long id,
                                              @RequestParam("imagen") MultipartFile archivo) {
        try {
            Imagen imagenActualizada = imagenService.actualizarImagenProducto(id, archivo);
            return ResponseEntity.ok()
                    .body(Map.of(
                            "mensaje", "Imagen actualizada con éxito",
                            "imagen", imagenActualizada
                    ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al actualizar la imagen: " + e.getMessage()));
        }
    }

    @DeleteMapping("/producto/{productoId}")
    public ResponseEntity<?> eliminarImagenesProducto(@PathVariable Long productoId) {
        try {
            imagenService.eliminarImagenesProducto(productoId);
            return ResponseEntity.ok()
                    .body(Map.of("mensaje", "Imágenes del producto eliminadas con éxito"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al eliminar las imágenes: " + e.getMessage()));
        }
    }

    @PatchMapping("/{id}/eliminar")
    public ResponseEntity<?> marcarComoEliminada(@PathVariable Long id) {
        try {
            imagenService.marcarImagenComoEliminada(id);
            return ResponseEntity.ok()
                    .body(Map.of("mensaje", "Imagen marcada como eliminada"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}