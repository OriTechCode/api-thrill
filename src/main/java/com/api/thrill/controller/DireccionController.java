package com.api.thrill.controller;

import com.api.thrill.entity.Direccion;
import com.api.thrill.service.DireccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.api.thrill.entity.Usuario;
import com.api.thrill.service.UsuarioService;
import java.util.List;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionController extends BaseController<Direccion, Long> {

    private final DireccionService direccionService;

    private final UsuarioService usuarioService;

    public DireccionController(DireccionService direccionService, UsuarioService usuarioService) {
        super(direccionService);
        this.direccionService = direccionService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Direccion>> findByUsuarioId(@PathVariable Long usuarioId) {

        return ResponseEntity.ok(direccionService.findByUsuarioId(usuarioId));
    }
    @Override
    @PostMapping
    public ResponseEntity<Direccion> create(@RequestBody Direccion direccion) {
        // Validar si el usuario existe
        if (direccion.getUsuario() == null || direccion.getUsuario().getId() == null) {
            throw new RuntimeException("El ID del usuario es obligatorio.");
        }

        Usuario usuario = usuarioService.buscarPorId(direccion.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + direccion.getUsuario().getId()));

        // Asignar el usuario a la dirección
        direccion.setUsuario(usuario);

        // Guardar la dirección
        Direccion nuevaDireccion = direccionService.save(direccion);
        return ResponseEntity.ok(nuevaDireccion);
    }
}