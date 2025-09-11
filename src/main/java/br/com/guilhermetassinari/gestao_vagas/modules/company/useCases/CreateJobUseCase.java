package br.com.guilhermetassinari.gestao_vagas.modules.company.useCases;

import br.com.guilhermetassinari.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.guilhermetassinari.gestao_vagas.modules.company.entities.JobEntity;
import br.com.guilhermetassinari.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.guilhermetassinari.gestao_vagas.modules.company.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateJobUseCase {
    private final JobRepository jobRepository;

    private final CompanyRepository companyRepository;

    public JobEntity execute(JobEntity jobEntity){
        // Easier way with method reference, more clean than lambda
        companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(
                CompanyNotFoundException::new
        );
        return this.jobRepository.save(jobEntity);
    }
}
