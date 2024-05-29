package br.com.alex.gestao_vagas.modules.company.useCases;

import br.com.alex.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.alex.gestao_vagas.modules.company.entities.JobEntity;
import br.com.alex.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.alex.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.stereotype.Service;

@Service
public record CreateJobUseCase(JobRepository jobRepository, CompanyRepository companyRepository) {
    public JobEntity execute(JobEntity jobEntity) {
        companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(() -> new CompanyNotFoundException());
        return this.jobRepository.save(jobEntity);
    }
}
