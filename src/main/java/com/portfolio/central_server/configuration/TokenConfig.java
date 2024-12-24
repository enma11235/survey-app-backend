package com.portfolio.central_server.configuration;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.*;

import javax.crypto.SecretKey;

@Configuration
public class TokenConfig {
    @Bean
    public SecretKey jwtSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}
