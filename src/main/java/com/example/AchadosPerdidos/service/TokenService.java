package br.com.achadosperdidos.api.service;

import br.com.achadosperdidos.api.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    
    @Value("${api.security.token.secret}")
    private String secret;
    
    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private static final String ISSUER = "achados-perdidos-api";
    private static final int HORAS_EXPIRACAO = 2;

    public String gerarToken(Usuario usuario) {
        if (usuario == null || usuario.getEmail() == null) {
            throw new IllegalArgumentException("Usuário e email não podem ser nulos");
        }
        
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(usuario.getEmail())
                .withClaim("roles", usuario.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .toList())
                .withExpiresAt(LocalDateTime.now()
                    .plusHours(HORAS_EXPIRACAO)
                    .toInstant(ZoneOffset.of("-03:00")))
                .sign(algoritmo);
        } catch (JWTCreationException e) {
            logger.error("Erro ao gerar token para usuário: {}", usuario.getEmail(), e);
            throw new RuntimeException("Falha ao gerar token", e);
        }
    }
    
    public String validarToken(String token) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException e) {
            logger.warn("Token inválido ou expirado", e);
            return null;
        }
    }
}