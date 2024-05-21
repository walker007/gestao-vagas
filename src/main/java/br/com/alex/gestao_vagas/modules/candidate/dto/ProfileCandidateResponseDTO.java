package br.com.alex.gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {
    @Schema(description = "Nome do candidato", example = "Alex")
    private String name;
    @Schema(description = "Email do candidato", example = "alex@email.com")
    private String email;
    @Schema(description = "Username do candidato", example = "alex")
    private String username;
    @Schema(description = "Descrição do candidato", example = "Desenvolvedor Java")
    private String description;
    private UUID id;
}
