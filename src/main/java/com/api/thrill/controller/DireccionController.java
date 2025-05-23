package com.api.thrill.controller;

import com.api.thrill.entity.Direccion;
import com.api.thrill.service.DireccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionController extends BaseController<Direccion, Long> {

    private final DireccionService direccionService;

    public DireccionController(DireccionService direccionService) {
        super(direccionService); // Pasamos el servicio al constructor de BaseController
        this.direccionService = direccionService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Direccion>> findByUsuarioId(@PathVariable Long usuarioId) {
        // Endpoint específico para el método findByUsuarioId
        return ResponseEntity.ok(direccionService.findByUsuarioId(usuarioId));
    }
}