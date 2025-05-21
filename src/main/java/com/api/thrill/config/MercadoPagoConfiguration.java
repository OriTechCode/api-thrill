package com.api.thrill.config;

import com.mercadopago.MercadoPagoConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;

@Configuration
public class MercadoPagoConfiguration {

    @Value("${mercadopago.access.token}")
    private String mercadoPagoAccessToken;

    @Value("${mercadopago.public.key}")
    private String mercadoPagoPublicKey;

    @PostConstruct
    public void initialize() {
        MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);
    }

    public String getPublicKey() {
        return mercadoPagoPublicKey;
    }
}