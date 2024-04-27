package br.com.alex.gestao_vagas.modules.company.controllers;

import br.com.alex.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.alex.gestao_vagas.modules.company.entities.JobEntity;
import br.com.alex.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company")
public class JobController {

    private final CreateJobUseCase createJobUseCase;

    @Autowired
    JobController(CreateJobUseCase createJobUseCase) {
        this.createJobUseCase = createJobUseCase;
    }

    @PostMapping("/job")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<?> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        try {
            JobEntity jobEntity = JobEntity.builder()
                    .description(createJobDTO.getDescription())
                    .benefits(createJobDTO.getBenefits())
                    .level(createJobDTO.getLevel())
                    .companyId(UUID.fromString(request.getAttribute("company_id").toString()))
                    .build();

            return ResponseEntity.ok().body(createJobUseCase.execute(jobEntity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}


