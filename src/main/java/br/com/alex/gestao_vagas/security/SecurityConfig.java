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

    private static final String[] PERMIT_ALL_LIST = {
            "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/actuator/**"
    };
    private final SecurityCompanyFilter securityCompanyFilter;
    private final SecurityCandidateFilter securityCandidateFilter;

    @Autowired
    SecurityConfig(SecurityCompanyFilter securityCompanyFilter, SecurityCandidateFilter securityCandidateFilter) {
        this.securityCompanyFilter = securityCompanyFilter;
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
                        .requestMatchers(PERMIT_ALL_LIST).permitAll()
                        .anyRequest().authenticated()
                ).addFilterBefore(securityCompanyFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class)
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
