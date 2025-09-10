package br.com.guilhermetassinari.gestao_vagas.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
public class TestUtils {
    private static Long tokenExpirationInSeconds;

    @Value("${security.token.expiration:3600}")
    public void setTokenExpirationInSeconds(Long tokenExpirationInSeconds) {
        TestUtils.tokenExpirationInSeconds = tokenExpirationInSeconds;
    }

    public static String objectToJSON(Object object){
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateToken(UUID idCompany, String secretKey){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        Instant now = Instant.now();
        Instant expiration = now.plus(Duration.ofSeconds(tokenExpirationInSeconds));


        return JWT.create()
                .withIssuer("javagas")
                .withSubject(idCompany.toString())
                .withClaim("roles", List.of("COMPANY"))
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .sign(algorithm);
    }
}
