package br.com.alex.gestao_vagas.modules.candidate.controllers;

import br.com.alex.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.alex.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.alex.gestao_vagas.modules.candidate.useCases.ProfileCancidadeUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public record CandidateController(CreateCandidateUseCase createCandidateUseCase,
                                  ProfileCancidadeUseCase profileCancidadeUseCase) {


    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            return ResponseEntity.ok().body(createCandidateUseCase.execute(candidateEntity));
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> get(HttpServletRequest request) {
        try {
            String idCandidate = request.getAttribute("candidate_id").toString();
            return ResponseEntity.ok().body(profileCancidadeUseCase.execute(UUID.fromString(idCandidate)));
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
}
