package com.aluracursos.forohub.service;

import com.aluracursos.forohub.exceptions.JwtInvalidException;
import com.aluracursos.forohub.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String createJwtToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("forohub")
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Error creating JWT token", e);
        }
    }

    // El método getSubject se utiliza para extraer el nombre de usuario (en este caso, email)
    // de un JWT después de verificar su validez
    public String getSubject(String jwtToken) {
        if (jwtToken == null) {
            throw new RuntimeException("JWT token is null");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            // Configura un verificador de JWT con el algoritmo de firma definido y el emisor esperado
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("forohub")
                    .build();
            // Verificar y decodificar el token JWT
            DecodedJWT decodedJWT = verifier.verify(jwtToken);
            // Obtiene el sujeto del JWT. El sujeto es el nombre de usuario del propietario del token (en este caso, el email)
            String subject = decodedJWT.getSubject();
            if (subject == null) {
                throw new RuntimeException("Invalid JWT token: subject is null");
            }
            return subject;
        } catch (JWTVerificationException e) {
            throw new JwtInvalidException("Invalid JWT token");
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}
