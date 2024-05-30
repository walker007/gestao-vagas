package br.com.alex.gestao_vagas.modules.candidate.useCases;

import br.com.alex.gestao_vagas.exceptions.UserNotFoundException;
import br.com.alex.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.alex.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.alex.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record ProfileCandidateUseCase(CandidateRepository repository) {

    public ProfileCandidateResponseDTO execute(UUID idCandidate) {
        CandidateEntity candidate = this.repository.findById(idCandidate)
                .orElseThrow(() -> new UserNotFoundException());
        ProfileCandidateResponseDTO candidateDto = ProfileCandidateResponseDTO.builder()
                .name(candidate.getName())
                .email(candidate.getEmail())
                .username(candidate.getUsername())
                .description(candidate.getDescription())
                .id(candidate.getId())
                .build();

        return candidateDto;
    }
}
