package br.com.guilhermetassinari.gestao_vagas.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTCandidateProvider {

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    // More robust way than printStackTrace, using a logger to show the errors
    private static final Logger logger = LoggerFactory.getLogger(JWTCompanyProvider.class);

    public DecodedJWT validateToken(String token) {
        token = token.replace("Bearer ", "");

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            return JWT.require(algorithm).build().verify(token);
        } catch (JWTVerificationException exception) {
            logger.error("JWT validation failed: {}", exception.getMessage(), exception);
            return null;
        }

    }
}
