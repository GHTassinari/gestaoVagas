package br.com.guilhermetassinari.gestao_vagas.modules.candidate.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "Daniel", requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Candidate's name")
    private String name;

    @Pattern(regexp = "\\S+", message = "The field [username] must not contain spaces")
    @Schema(example = "daniel", requiredMode = Schema.RequiredMode.REQUIRED
    , description = "Candidate's username")

    private String username;

    @Email(message = "The field [email] must be a valid e-mail")
    @Schema(example = "daniel@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED
    , description = "Candidate's e-mail")
    private String email;

    @Length(min = 10, max = 100, message = "The password must contain between (10) and (100) characters")
    @Schema(example = "admin@1234", minLength = 10, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Candidate's password")
    private String password;

    @Schema(example = "Java developer", requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Short Candidate's description")
    private String description;

    @Schema(
            description = "Candidate's curriculum vitae",
            example = "Daniel | Java Developer | 5+ years experience with Spring Boot, microservices, and AWS"
    )
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
