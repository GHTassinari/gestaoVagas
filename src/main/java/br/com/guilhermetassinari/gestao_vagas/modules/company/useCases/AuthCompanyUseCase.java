package br.com.guilhermetassinari.gestao_vagas.modules.company.useCases;

import br.com.guilhermetassinari.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.guilhermetassinari.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.guilhermetassinari.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class AuthCompanyUseCase {

    @Value("${security.token.secret.company}")
    private String secretKey;

    @Value("${security.token.expiration:3600}")
    private Long tokenExpirationInSeconds;

    private final CompanyRepository companyRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) {
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername());

        // Supplier postpone the execution, being a function that doesn't receive any arguments. But that returns a value
        // It's function it's to postpone de operation execution
        Supplier<BadCredentialsException> exception =
                () -> new BadCredentialsException("Username/Password is incorrect");

        if (company.isEmpty()) {
            throw exception.get();
        }

        var foundCompany = company.get();

        if (!this.passwordEncoder.matches(authCompanyDTO.getPassword(), foundCompany.getPassword())) {
            throw exception.get();
        }

        Instant now = Instant.now();
        Instant expiration = now.plus(Duration.ofSeconds(tokenExpirationInSeconds));

        var token = generateToken(foundCompany.getId().toString(), expiration, now);

        return AuthCompanyResponseDTO.builder()
                .accessToken(token)
                .expires_in(expiration.toEpochMilli())
                .build();
    }

    private String generateToken(String companyId, Instant expiration, Instant now){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);


        return JWT.create()
                .withIssuer("javagas")
                .withSubject(companyId)
                .withClaim("roles", List.of("COMPANY"))
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .sign(algorithm);

    }
}
