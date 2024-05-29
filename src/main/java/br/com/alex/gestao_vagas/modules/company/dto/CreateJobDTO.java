package br.com.alex.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateJobDTO {
    @Schema(description = "Nome da vaga", example = "Desenvolvedor Java", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
    @Schema(description = "Benefícios da vaga", example = "Vale transporte, vale refeição", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;
    @Schema(description = "Nível da vaga", example = "Júnior", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;
}
