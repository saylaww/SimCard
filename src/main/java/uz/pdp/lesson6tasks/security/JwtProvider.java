package uz.pdp.lesson6tasks.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.lesson6tasks.entity.Role;

import java.util.Date;


@Component
public class JwtProvider {

    private static final long expireTime = 60 * 60 * 24000;
    private static final String secretKey = "secretKey";

    public String getEmailFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
    public String generateToken(String username, Role role) {
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .claim("roles", role)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }


}
