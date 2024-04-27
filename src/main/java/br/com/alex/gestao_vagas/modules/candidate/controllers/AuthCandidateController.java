package br.com.alex.gestao_vagas.modules.candidate.controllers;

import br.com.alex.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.alex.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.alex.gestao_vagas.modules.candidate.useCases.AuthCandidateUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public record AuthCandidateController(AuthCandidateUseCase authCandidateUseCase) {
    @PostMapping("/auth")
    public ResponseEntity<?> authCandidate(AuthCandidateRequestDTO auth) {
        try {
            AuthCandidateResponseDTO token = this.authCandidateUseCase.execute(auth);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
