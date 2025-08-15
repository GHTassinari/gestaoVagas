package br.com.guilhermetassinari.gestao_vagas.modules.candidate.useCases;

import br.com.guilhermetassinari.gestao_vagas.exceptions.UserFoundException;
import br.com.guilhermetassinari.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.guilhermetassinari.gestao_vagas.modules.candidate.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCandidateUseCase {

    private final CandidateRepository candidateRepository;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository
                .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        return this.candidateRepository.save(candidateEntity);
    }
}
