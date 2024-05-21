package br.com.alex.gestao_vagas.modules.candidate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "candidate")
@Data
public class CandidateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty()
    @Schema(description = "Nome do candidato", example = "Alex", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotEmpty()
    @Schema(description = "Username do candidato", example = "alex", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "\\S+", message = "o campo username nao pode conter espaco")
    private String username;

    @NotEmpty()
    @Schema(description = "Email do candidato", example = "alex@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @Email(message = "Informe um email válido")
    private String email;

    @NotEmpty()
    @Length(min = 10, max = 100)
    @Schema(description = "Senha do candidato", example = "admin@123456", minLength = 10, maxLength = 100,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    @Schema(description = "Descrição do candidato", example = "Desenvolvedor Java", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
