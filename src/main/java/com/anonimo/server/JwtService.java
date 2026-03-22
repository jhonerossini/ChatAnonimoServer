package com.anonimo.server;

import java.time.Duration;
import java.util.Set;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtService {

	public String gerarToken(String usuario) {
        return Jwt.issuer("chat-anonimo")
                .subject(usuario)
                .groups(Set.of("USER"))
                .expiresIn(Duration.ofHours(2))
                .sign();
    }
}
