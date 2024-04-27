package br.com.alex.gestao_vagas.modules.candidate.useCases;

import br.com.alex.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.alex.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.alex.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.alex.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AuthCandidateUseCase {

    private final CandidateRepository candidateRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${security.token.secret.candidate}")
    private String secret;

    AuthCandidateUseCase(CandidateRepository candidateRepository, PasswordEncoder passwordEncoder) {
        this.candidateRepository = candidateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
        CandidateEntity candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
                .orElseThrow(() -> new UsernameNotFoundException("Candidate/Password incorrect"));
        boolean passwordMatch = this.passwordEncoder.matches(authCandidateRequestDTO.password(),
                candidate.getPassword());
        if (!passwordMatch) {
            throw new AuthenticationException("Candidate/Password incorrect");
        }

        Algorithm algorithm = Algorithm.HMAC256(secret);
        Instant expiresIn = Instant.now().plus(Duration.ofHours(2));
        String token = JWT.create()
                .withIssuer("Javagas")
                .withSubject(candidate.getId().toString())
                .withExpiresAt(expiresIn)
                .withClaim("roles", List.of("CANDIDATE"))
                .sign(algorithm);
        return AuthCandidateResponseDTO.builder()
                .expires_in(expiresIn.toEpochMilli())
                .access_token(token)
                .build();
    }
}
