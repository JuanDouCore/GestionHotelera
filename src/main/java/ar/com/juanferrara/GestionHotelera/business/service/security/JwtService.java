package ar.com.juanferrara.GestionHotelera.business.service.security;

import ar.com.juanferrara.GestionHotelera.domain.entity.Usuario;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.expiration-minutes}")
    private long EXPIRATION_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generateToken(Usuario usuario) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + (EXPIRATION_MINUTES * 60 * 1000));

        return Jwts.builder()
                .setClaims(generateExtraClaims(usuario))
                .setSubject(usuario.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key generateKey() {
        byte[] secretAsBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(secretAsBytes);
    }

    public void validateToken(String jwt) {
        Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(jwt);
    }

    public String extractUsername(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }

    private Map<String, Object> generateExtraClaims(Usuario usuario) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("nombre", usuario.getNombre());
        extraClaims.put("rol", usuario.getRole().name());
        extraClaims.put("authorities", usuario.getAuthorities());
        return extraClaims;
    }
}
