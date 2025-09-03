package br.com.guilhermetassinari.gestao_vagas.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthCandidateResponseDTO {
    private String acessToken;
}
