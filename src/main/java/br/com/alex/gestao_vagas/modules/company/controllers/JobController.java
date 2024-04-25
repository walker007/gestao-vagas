package br.com.alex.gestao_vagas.modules.company.controllers;

import br.com.alex.gestao_vagas.modules.company.entities.JobEntity;
import br.com.alex.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public record JobController(CreateJobUseCase createJobUseCase) {

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody JobEntity jobEntity) {
        try {
            return ResponseEntity.ok().body(createJobUseCase.execute(jobEntity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
