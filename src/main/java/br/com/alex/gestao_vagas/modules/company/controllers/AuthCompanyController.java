package br.com.alex.gestao_vagas.modules.company.controllers;

import br.com.alex.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.alex.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/company")
public class AuthCompanyController {
    private final AuthCompanyUseCase authCompanyUseCase;

    @Autowired
    AuthCompanyController(AuthCompanyUseCase authCompanyUseCase) {
        this.authCompanyUseCase = authCompanyUseCase;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> create(@RequestBody AuthCompanyDTO authCompanyDTO) {
        try {
            return ResponseEntity.ok(this.authCompanyUseCase.execute(authCompanyDTO));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }
}
