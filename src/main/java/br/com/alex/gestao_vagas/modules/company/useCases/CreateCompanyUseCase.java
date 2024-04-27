package br.com.alex.gestao_vagas.modules.company.useCases;

import br.com.alex.gestao_vagas.exceptions.UserFoundException;
import br.com.alex.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.alex.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public record CreateCompanyUseCase(CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {

    public CompanyEntity execute(CompanyEntity companyEntity) {
        this.companyRepository
                .findByEmailOrUsername(companyEntity.getEmail(), companyEntity.getUsername())
                .ifPresent(user -> {
                    throw new UserFoundException();
                });
        String password = this.passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);

        return this.companyRepository.save(companyEntity);
    }
}
