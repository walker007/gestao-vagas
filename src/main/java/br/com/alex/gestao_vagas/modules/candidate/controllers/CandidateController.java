package br.com.alex.gestao_vagas.modules.candidate.controllers;

import br.com.alex.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.alex.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.alex.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.alex.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.alex.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.alex.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.alex.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.alex.gestao_vagas.modules.company.entities.JobEntity;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Candidato", description = "Informações do candidato")
@RequestMapping("/candidate")
public class CandidateController {
    private final CreateCandidateUseCase createCandidateUseCase;
    private final ProfileCandidateUseCase profileCancidadeUseCase;
    private final ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;
    private final ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Autowired
    CandidateController(CreateCandidateUseCase createCandidateUseCase,
                        ProfileCandidateUseCase profileCancidadeUseCase,
                        ListAllJobsByFilterUseCase listAllJobsByFilterUseCase,
                        ApplyJobCandidateUseCase applyJobCandidateUseCase) {
        this.createCandidateUseCase = createCandidateUseCase;
        this.profileCancidadeUseCase = profileCancidadeUseCase;
        this.listAllJobsByFilterUseCase = listAllJobsByFilterUseCase;
        this.applyJobCandidateUseCase = applyJobCandidateUseCase;
    }

    @PostMapping("/")

    @Operation(summary = "Cadastro de Candidato", description = "Essa função é responsável por " +
            "cadastrar um novo candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CandidateEntity.class))
            }), @ApiResponse(responseCode = "422", description = "Usuário Já existe"),
    })

    public ResponseEntity<?> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            return ResponseEntity.ok().body(createCandidateUseCase.execute(candidateEntity));
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")

    @Operation(summary = "Perfil do Candidato", description = "Essa função é responsável por " +
            "retornar o perfil do candidato")
    @SecurityRequirement(name = "jwt_auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
            }), @ApiResponse(responseCode = "400", description = "User Not Found"),
    })
    public ResponseEntity<?> get(HttpServletRequest request) {
        try {
            String idCandidate = request.getAttribute("candidate_id").toString();
            return ResponseEntity.ok().body(profileCancidadeUseCase.execute(UUID.fromString(idCandidate)));
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listar vagas disponíveis para o candidato", description = "Essa função é responsável por " +
            "listar as vagas disponíveis no filtro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobsByFilter(@RequestParam String filter) {
        return this.listAllJobsByFilterUseCase.execute(filter);
    }

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "Inscrição do candidato para uma vaga", description = "Essa função é responsável por " +
            "realizar a inscrição do candidato para uma vaga")
    public ResponseEntity<?> applyJob(HttpServletRequest request, @RequestBody UUID jobId) {
        try {
            String idCandidate = request.getAttribute("candidate_id").toString();
            ApplyJobEntity result = applyJobCandidateUseCase.execute(UUID.fromString(idCandidate), jobId);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
}
