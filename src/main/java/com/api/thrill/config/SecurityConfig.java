package com.api.thrill.config;

import com.api.thrill.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers(
                                        "/v3/api-docs/**",   // Documentación OpenAPI
                                        "/swagger-ui/**",    // Recursos de Swagger UI
                                        "/swagger-ui.html",  // Interfaz gráfica de Swagger
                                        "/api/auth/**" ,      // Rutas públicas de autenticación

                                        "/api/productos/**",   // ← rutas públicas GET
                                        "/api/imagenes/**",
                                        "/api/categorias/**",
                                        "/api/usuarios/**",
                                        "/api/tipos/**",
                                        "/api/talles/**",
                                        "/api/subcategorias/**",
                                        "/api/producto-talle/**",
                                        "/api/pagos/**",
                                        "/api/direcciones/**",
                                        "/api/detalles-orden/**",
                                        "/api/descuentos/**"
                                        
                                ).permitAll() // Permitir acceso público a estas rutas
                                .anyRequest().authenticated() // Requiere autenticación para el resto
                )
                .sessionManagement(sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}