package br.com.alex.gestao_vagas.security;

import br.com.alex.gestao_vagas.providers.JWTCandidateProvider;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class SecurityCandidateFilter extends OncePerRequestFilter {
    private final JWTCandidateProvider jwtCandidateProvider;

    SecurityCandidateFilter(JWTCandidateProvider jwtCandidateProvider) {
        this.jwtCandidateProvider = jwtCandidateProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException,
            IOException {

        String header = request.getHeader("Authorization");
        if (request.getRequestURI().startsWith("/candidate")) {
            if (header == null || !header.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            DecodedJWT tokenDecoded = this.jwtCandidateProvider.validateToken(header);
            if (tokenDecoded == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            request.setAttribute("candidate_id", tokenDecoded.getSubject());
            List<String> roles = tokenDecoded.getClaim("roles").asList(String.class);

            List<SimpleGrantedAuthority> authorities =
                    roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())).toList();

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(tokenDecoded.getSubject(), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

}
