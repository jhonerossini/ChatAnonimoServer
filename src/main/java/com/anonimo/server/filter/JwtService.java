package com.anonimo.server.filter;

import java.util.Date;

import javax.crypto.SecretKey;

import com.anonimo.server.repository.LoginRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class JwtService {
	
	private static final String SECRET = "minha-chave-super-secreta-123456789";

	private SecretKey getKey() {
	    return Keys.hmacShaKeyFor(SECRET.getBytes());
	}
	
	@Inject
	LoginRepository loginRepository;

    public String gerarToken(String usuario) {
        return Jwts.builder()
                .subject(usuario)
                .issuer("chat-anonimo")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2)) // 2h
                .signWith(getKey())
                .compact();
    }
    
    public Claims validarToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (Exception e) {
            return null; // token inválido
        }
    }
    
    protected String extrairToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
