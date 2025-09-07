package br.com.guilhermetassinari.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateJobDTO {

    @Schema(example = "Job vacancy for a junior developer", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
    @Schema(example = "GYMPASS, Health Plan", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;
    @Schema(example = "JUNIOR", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;
}
