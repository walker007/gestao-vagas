package br.com.alex.gestao_vagas.modules.candidate.useCases;

import br.com.alex.gestao_vagas.exceptions.UserFoundException;
import br.com.alex.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.alex.gestao_vagas.modules.candidate.CandidateRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public record CreateCandidateUseCase(CandidateRepository candidateRepository, PasswordEncoder passwordEncoder) {

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        
        this.candidateRepository
                .findByEmailOrUsername(candidateEntity.getEmail(), candidateEntity.getUsername())
                .ifPresent(user -> {
                    throw new UserFoundException();
                });

        String password = this.passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(password);

        return this.candidateRepository.save(candidateEntity);
    }
}
