package com.api.thrill.controller;

import com.api.thrill.dto.AuthRequest;
import com.api.thrill.dto.AuthResponse;
import com.api.thrill.entity.Usuario;
import com.api.thrill.repository.UsuarioRepository;
import com.api.thrill.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {

        Optional<Usuario> existente = usuarioRepository.findByEmail(usuario.getEmail());
        if (existente.isPresent()) {
            return ResponseEntity.badRequest().body("El email ya está registrado");
        }

        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        usuario.setRol("USER");
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getContrasena()
                    )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(authRequest.getEmail());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
