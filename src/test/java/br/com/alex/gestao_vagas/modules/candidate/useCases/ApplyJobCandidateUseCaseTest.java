package br.com.alex.gestao_vagas.modules.candidate.useCases;


import br.com.alex.gestao_vagas.exceptions.JobNotFoundException;
import br.com.alex.gestao_vagas.exceptions.UserNotFoundException;
import br.com.alex.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.alex.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.alex.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.alex.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.alex.gestao_vagas.modules.company.entities.JobEntity;
import br.com.alex.gestao_vagas.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private JobRepository jobRepository;
    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Shouldn't be able to apply for a job when candidate not found")
    public void should_not_be_able_to_apply_for_a_job_when_candidate_not_found() {
        try {
            applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }

    }

    @Test
    public void should_not_be_able_to_apply_for_a_job_when_job_not_found() {
        UUID idCandidate = UUID.randomUUID();
        CandidateEntity candidateEntity = new CandidateEntity();
        candidateEntity.setId(idCandidate);

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidateEntity));


        try {
            applyJobCandidateUseCase.execute(idCandidate, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }

    }

    @Test

    public void should_be_able_to_apply_for_a_job() {
        UUID idCandidate = UUID.randomUUID();
        UUID idJob = UUID.randomUUID();

        ApplyJobEntity applyJobEntity = ApplyJobEntity.builder()
                .candidateId(idCandidate)
                .jobId(idJob)
                .build();
        ApplyJobEntity applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build();


        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));

        when(applyJobRepository.save(applyJobEntity)).thenReturn(applyJobCreated);

        ApplyJobEntity applyJob = applyJobCandidateUseCase.execute(idCandidate, idJob);

        assertThat(applyJob).hasFieldOrProperty("id");
        assertNotNull(applyJob.getId());
    }
}
