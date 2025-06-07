package com.api.thrill.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Thrill API")
                        .description("Documentación de la API de Thrill E-commerce")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Soporte de Thrill")
                                .email("soporte@thrill.com")
                                .url("https://thrill.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación completa")
                        .url("https://thrill.com/docs"));
    }
}