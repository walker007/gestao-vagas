package br.com.alex.gestao_vagas.modules.candidate.controllers;

import br.com.alex.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.alex.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.alex.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    private final CreateCandidateUseCase createCandidateUseCase;
    private final ProfileCandidateUseCase profileCancidadeUseCase;

    @Autowired
    CandidateController(CreateCandidateUseCase createCandidateUseCase,
                        ProfileCandidateUseCase profileCancidadeUseCase) {
        this.createCandidateUseCase = createCandidateUseCase;
        this.profileCancidadeUseCase = profileCancidadeUseCase;
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
}
