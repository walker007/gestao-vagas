package br.com.alex.gestao_vagas.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTProvider {
    @Value("${security.token.secret}")
    private String secret;

    public String validateToken(String token) {
        token = token.replace("Bearer ", "");
        try {
            String subject = JWT.require(Algorithm.HMAC256(secret)).build().verify(token).getSubject();
            return subject;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
