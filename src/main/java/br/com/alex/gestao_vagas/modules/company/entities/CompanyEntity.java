package br.com.alex.gestao_vagas.modules.company.entities;

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

@Data
@Entity(name = "company")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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
    private String website;
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
