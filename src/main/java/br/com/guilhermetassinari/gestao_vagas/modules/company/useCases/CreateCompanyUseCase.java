package br.com.guilhermetassinari.gestao_vagas.modules.company.useCases;

import br.com.guilhermetassinari.gestao_vagas.exceptions.UserFoundException;
import br.com.guilhermetassinari.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.guilhermetassinari.gestao_vagas.modules.company.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCompanyUseCase {
    private final CompanyRepository companyRepository;

    public CompanyEntity execute(CompanyEntity companyEntity) {
        this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });
        return this.companyRepository.save(companyEntity);
    }
}
