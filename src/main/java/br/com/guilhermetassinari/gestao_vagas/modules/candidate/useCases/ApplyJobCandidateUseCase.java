package br.com.guilhermetassinari.gestao_vagas.modules.candidate.useCases;

import br.com.guilhermetassinari.gestao_vagas.exceptions.JobNotFoundException;
import br.com.guilhermetassinari.gestao_vagas.exceptions.UserNotFoundException;
import br.com.guilhermetassinari.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.guilhermetassinari.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.guilhermetassinari.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.guilhermetassinari.gestao_vagas.modules.company.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplyJobCandidateUseCase {

    private final CandidateRepository candidateRepository;

    private final JobRepository jobRepository;

    private final ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {
        this.candidateRepository.findById(idCandidate).orElseThrow(UserNotFoundException::new);

        this.jobRepository.findById(idJob).orElseThrow(JobNotFoundException::new);

        var applyJob = ApplyJobEntity.builder()
                .candidateId(idCandidate)
                .jobId(idJob)
                .build();

        applyJob = applyJobRepository.save(applyJob);

        return applyJob;
    }
}
