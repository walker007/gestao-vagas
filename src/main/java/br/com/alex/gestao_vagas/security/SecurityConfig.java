package br.com.alex.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;
    private final SecurityCandidateFilter securityCandidateFilter;

    @Autowired
    SecurityConfig(SecurityFilter securityFilter, SecurityCandidateFilter securityCandidateFilter) {
        this.securityFilter = securityFilter;
        this.securityCandidateFilter = securityCandidateFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/candidate").permitAll()
                        .requestMatchers("/company").permitAll()
                        .requestMatchers("/candidate/auth").permitAll()
                        .requestMatchers("/company/auth").permitAll()
                        .anyRequest().authenticated()
                ).addFilterBefore(securityFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class)
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
