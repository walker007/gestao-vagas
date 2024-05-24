package br.com.alex.gestao_vagas.modules.candidate.useCases;

import br.com.alex.gestao_vagas.exceptions.JobNotFoundException;
import br.com.alex.gestao_vagas.exceptions.UserNotFoundException;
import br.com.alex.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.alex.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record ApplyJobCandidateUseCase(CandidateRepository candidateRepository,
                                       JobRepository jobRepository
) {
    public void execute(UUID candidateId, UUID jobId) {
        this.candidateRepository.findById(candidateId)
                .orElseThrow(() -> new UserNotFoundException());

        this.jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException());

        
    }
}
