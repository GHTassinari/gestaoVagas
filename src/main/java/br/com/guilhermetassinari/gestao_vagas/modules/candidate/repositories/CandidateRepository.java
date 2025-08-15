package br.com.guilhermetassinari.gestao_vagas.modules.candidate.repositories;

import br.com.guilhermetassinari.gestao_vagas.modules.candidate.entities.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {
        Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);
}
