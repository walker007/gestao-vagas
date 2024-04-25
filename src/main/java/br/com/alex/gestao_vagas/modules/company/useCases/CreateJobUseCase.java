package br.com.alex.gestao_vagas.modules.company.useCases;

import br.com.alex.gestao_vagas.modules.company.entities.JobEntity;
import br.com.alex.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.stereotype.Service;

@Service
public record CreateJobUseCase(JobRepository jobRepository) {
    public JobEntity execute(JobEntity jobEntity) {
        return this.jobRepository.save(jobEntity);
    }
}
