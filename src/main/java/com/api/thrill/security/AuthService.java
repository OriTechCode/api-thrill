package com.api.thrill.security;

import com.api.thrill.dto.AuthResponse;
import com.api.thrill.dto.LoginRequest;
import com.api.thrill.dto.RegisterRequest;
import com.api.thrill.dto.UsuarioDTO;
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
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private UsuarioDTO convertToDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .imagenPerfil(usuario.getImagenPerfil())
                .direcciones(usuario.getDirecciones())
                .eliminado(usuario.getEliminado())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(usuario);

        return AuthResponse.builder()
                .token(token)
                .usuario(convertToDTO(usuario))
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol("USER")
                .imagenPerfil(null)
                .ordenes(new ArrayList<>())
                .direcciones(new ArrayList<>())
                .build();

        usuarioRepository.save(usuario);
        String token = jwtService.generateToken(usuario);

        return AuthResponse.builder()
                .token(token)
                .usuario(convertToDTO(usuario))
                .build();
    }
}