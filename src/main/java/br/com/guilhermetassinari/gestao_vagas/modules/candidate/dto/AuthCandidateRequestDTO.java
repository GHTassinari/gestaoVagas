package br.com.guilhermetassinari.gestao_vagas.modules.candidate.dto;

// In a record, values cannot be changed after initialization.
public record AuthCandidateRequestDTO(String username, String password) {

}
