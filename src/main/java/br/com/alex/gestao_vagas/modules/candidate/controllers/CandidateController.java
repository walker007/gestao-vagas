package br.com.alex.gestao_vagas.modules.candidate.controllers;

import br.com.alex.gestao_vagas.modules.candidate.CandidateEntity;
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
@RequestMapping("/candidate")
public class CandidateController {
    private final CreateCandidateUseCase createCandidateUseCase;
    private final ProfileCandidateUseCase profileCancidadeUseCase;
    private final ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Autowired
    CandidateController(CreateCandidateUseCase createCandidateUseCase,
                        ProfileCandidateUseCase profileCancidadeUseCase,
                        ListAllJobsByFilterUseCase listAllJobsByFilterUseCase) {
        this.createCandidateUseCase = createCandidateUseCase;
        this.profileCancidadeUseCase = profileCancidadeUseCase;
        this.listAllJobsByFilterUseCase = listAllJobsByFilterUseCase;
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            return ResponseEntity.ok().body(createCandidateUseCase.execute(candidateEntity));
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
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
    @Tag(name = "Candidato", description = "Informações do candidato")
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
}
