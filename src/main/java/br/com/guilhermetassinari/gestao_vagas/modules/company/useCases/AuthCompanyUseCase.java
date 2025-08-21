package br.com.guilhermetassinari.gestao_vagas.modules.company.useCases;

import br.com.guilhermetassinari.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.guilhermetassinari.gestao_vagas.modules.company.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthCompanyUseCase {

    private final CompanyRepository companyRepository;

    private final PasswordEncoder passwordEncoder;

    public void execute(AuthCompanyDTO authCompanyDTO) {
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Company not found")
        );

        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        if(!passwordMatches) {
            throw new BadCredentialsException("Invalid credentials");
        }
    }
}
