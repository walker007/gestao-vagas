package br.com.alex.gestao_vagas.modules.company.controllers;

import br.com.alex.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.alex.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public record CompanyController(CreateCompanyUseCase createCompanyUseCase) {

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody CompanyEntity companyEntity) {
        try {
            return ResponseEntity.ok(this.createCompanyUseCase.execute(companyEntity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
