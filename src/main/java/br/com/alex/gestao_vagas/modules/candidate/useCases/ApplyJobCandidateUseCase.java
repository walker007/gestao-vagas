package br.com.alex.gestao_vagas.modules.candidate.useCases;

import br.com.alex.gestao_vagas.exceptions.JobNotFoundException;
import br.com.alex.gestao_vagas.exceptions.UserNotFoundException;
import br.com.alex.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.alex.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.alex.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.alex.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record ApplyJobCandidateUseCase(CandidateRepository candidateRepository,
                                       JobRepository jobRepository,
                                       ApplyJobRepository applyJobRepository
) {
    public ApplyJobEntity execute(UUID candidateId, UUID jobId) {
        this.candidateRepository.findById(candidateId)
                .orElseThrow(() -> new UserNotFoundException());

        this.jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException());

        ApplyJobEntity applyJobEntity = ApplyJobEntity.builder()
                .candidateId(candidateId)
                .jobId(jobId)
                .build();
        return applyJobRepository.save(applyJobEntity);
    }
}
