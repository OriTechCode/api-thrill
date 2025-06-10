package com.api.thrill.controller;

import com.api.thrill.entity.Usuario;
import com.api.thrill.service.UsuarioService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController extends BaseController<Usuario, Long> {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        super(usuarioService);
        this.usuarioService = usuarioService;
    }

    // Métodos adicionales específicos del Usuario
    @GetMapping("/buscar/email")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public boolean existePorEmail(@RequestParam String email) {
        return usuarioService.existePorEmail(email);
    }
}