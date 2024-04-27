package br.com.alex.gestao_vagas.modules.company.useCases;

import br.com.alex.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.alex.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.alex.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;

@Service
public class AuthCompanyUseCase {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${security.token.secret}")
    private String secret;

    @Autowired
    AuthCompanyUseCase(CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        CompanyEntity company =
                this.companyRepository.findByUsername(authCompanyDTO.getUsername())
                        .orElseThrow(() -> new UsernameNotFoundException("Username/password incorrect"));

        boolean passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException("Username/password incorrect");
        }

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create().withIssuer("Javagas")
                .withSubject(company.getId().toString())
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .sign(algorithm);
    }

}
