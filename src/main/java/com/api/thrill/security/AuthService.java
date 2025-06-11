package com.api.thrill.security;


import com.api.thrill.dto.AuthResponse;
import com.api.thrill.dto.LoginRequest;
import com.api.thrill.dto.RegisterRequest;
import com.api.thrill.entity.Usuario;
import com.api.thrill.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtservice;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails usuario = usuarioRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtservice.getToken(usuario);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(request.getRol())
                .imagenPerfil(null)
                .ordenes(new ArrayList<>())
                .direcciones(new ArrayList<>())
                .build();
        usuarioRepository.save(usuario);
        return AuthResponse.builder()
                .token(jwtservice.getToken(usuario))
                .build();
    }

}
