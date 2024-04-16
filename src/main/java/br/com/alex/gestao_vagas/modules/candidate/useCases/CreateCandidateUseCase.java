package br.com.alex.gestao_vagas.modules.candidate.useCases;

import br.com.alex.gestao_vagas.exceptions.UserFoundException;
import br.com.alex.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.alex.gestao_vagas.modules.candidate.CandidateRepository;
import org.springframework.stereotype.Service;

@Service
public record CreateCandidateUseCase(CandidateRepository candidateRepository) {
    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository.findByEmailOrUsername(candidateEntity.getEmail(), candidateEntity.getUsername()).ifPresent(user -> {
            throw new UserFoundException();
        });

        return this.candidateRepository.save(candidateEntity);
    }
}
