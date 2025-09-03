package br.com.guilhermetassinari.gestao_vagas.modules.candidate.useCases;

import br.com.guilhermetassinari.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.guilhermetassinari.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.guilhermetassinari.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service // Service of candidate
@RequiredArgsConstructor
public class AuthCandidateUseCase {

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    @Value("${security.token.expiration:3600}")
    private Long tokenExpirationInSeconds;

    private final CandidateRepository candidateRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
                .orElseThrow(() -> new UsernameNotFoundException("Username or password is incorrect"));

        var passwordMatches = this.passwordEncoder
                .matches(authCandidateRequestDTO.password(),  candidate.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        Instant now = Instant.now();
        Instant expiration = now.plus(Duration.ofSeconds(tokenExpirationInSeconds));

        var token = JWT.create()
                .withIssuer("javagas")
                .withSubject(candidate.getId().toString())
                .withClaim("roles", List.of("CANDIDATE"))
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .sign(algorithm);

        return AuthCandidateResponseDTO.builder()
                .accessToken(token)
                .expires_in(expiration.toEpochMilli())
                .build();
    }
}
