package com.api.thrill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173") // Desarrollo y Producción
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos
                        .allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "Accept") // Cabeceras explícitas
                        .allowCredentials(true); // Permitir cookies/autenticación
            }
        };
    }
}