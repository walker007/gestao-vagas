package br.com.alex.gestao_vagas.modules.company.useCases;

import br.com.alex.gestao_vagas.exceptions.UserFoundException;
import br.com.alex.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.alex.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public record CreateCompanyUseCase(CompanyRepository companyRepository) {
    public CompanyEntity execute(CompanyEntity companyEntity) {
        this.companyRepository
                .findByEmailOrUsername(companyEntity.getEmail(), companyEntity.getUsername())
                .ifPresent(user -> {
                    throw new UserFoundException();
                });

        return this.companyRepository.save(companyEntity);
    }
}
