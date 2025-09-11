package br.com.guilhermetassinari.gestao_vagas.modules.company.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name="company")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Pattern(regexp = "\\S+", message = "The field [username] must not contain spaces")
    private String username;

    @Email(message = "The field [email] must be a valid e-mail")
    private String email;

    @Length(min = 10, max = 100, message = "The password must contain between (10) and (100) characters")
    private String password;
    private String website;
    private String description;

    private String name;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
