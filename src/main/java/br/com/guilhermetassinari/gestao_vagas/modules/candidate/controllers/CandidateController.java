package br.com.guilhermetassinari.gestao_vagas.modules.candidate.controllers;

import br.com.guilhermetassinari.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.guilhermetassinari.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.guilhermetassinari.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.guilhermetassinari.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.guilhermetassinari.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.guilhermetassinari.gestao_vagas.modules.company.entities.JobEntity;
import br.com.guilhermetassinari.gestao_vagas.modules.company.useCases.ListAllJobsByFilterUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@RequiredArgsConstructor
@Tag(name = "Candidate", description = "Information related to the Candidate")
public class CandidateController {

    private final CreateCandidateUseCase createCandidateUseCase;

    private final ProfileCandidateUseCase profileCandidateUseCase;

    private final ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    private final ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @PostMapping("/")
    @Operation(summary = "Register the candidate",
            description = "This function is responsible to register the candidate")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = CandidateEntity.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "User already exists")
    }
    )
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            var result = this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listing the candidate profile",
            description = "This function is responsible to search for the user's profile info")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = ProfileCandidateResponseDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "User not found")
    }
    )
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest request) {
        var idCandidate = request.getAttribute("candidate_id");

        try {
            var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // As you need to be logged in as a candidate, it makes more sense for the route
    // that list all jobs to be in the CandidateController
    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listing all the job vacancies for the candidate",
            description = "This function is responsible to list all the Job vacancies list, based on the filter")
    @ApiResponses(@ApiResponse(responseCode = "200", content = {
            @Content(
                    array = @ArraySchema(schema = @Schema(implementation = JobEntity.class))
            )
    }))
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter) {
        return this.listAllJobsByFilterUseCase.execute(filter);
    }


    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Candidate applies to a job vacancy",
            description = "This function is responsible to apply the candidate to the job vacancy")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> ApplyJob(HttpServletRequest request, @RequestBody UUID idJob) {
        var idCandidate = request.getAttribute("candidate_id");

        try {
            var result = this.applyJobCandidateUseCase.execute(UUID.fromString(idCandidate.toString()), idJob);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
