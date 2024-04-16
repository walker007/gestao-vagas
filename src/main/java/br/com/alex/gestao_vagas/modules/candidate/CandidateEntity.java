package br.com.alex.gestao_vagas.modules.candidate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;
@Data
public class CandidateEntity {
    private UUID id;
    @NotEmpty()
    private String name;
    @NotEmpty()
    @Pattern(regexp = "\\S+", message = "o campo username nao pode conter espaco")
    private String username;
    @NotEmpty()
    @Email(message = "Informe um email válido")
    private String email;
    @NotEmpty()
    @Length(min = 10, max = 100)
    private String password;
    private String description;
    private String curriculum;
}
