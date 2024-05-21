package br.com.alex.gestao_vagas.modules.candidate.useCases;

import br.com.alex.gestao_vagas.modules.company.entities.JobEntity;
import br.com.alex.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record ListAllJobsByFilterUseCase(JobRepository jobRepository) {
    public List<JobEntity> execute(String filter) {
        return this.jobRepository.findByDescriptionContainingIgnoreCase(filter);
    }
}
