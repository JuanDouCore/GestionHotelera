package ar.com.juanferrara.GestionHotelera.business.service.security;

import ar.com.juanferrara.GestionHotelera.domain.dto.security.TokenRefreshRequest;
import ar.com.juanferrara.GestionHotelera.domain.dto.security.TokenRefreshResponse;
import ar.com.juanferrara.GestionHotelera.domain.entity.RefreshToken;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.NotFoundException;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.TokenRefreshException;
import ar.com.juanferrara.GestionHotelera.persistence.RefreshTokenRepository;
import ar.com.juanferrara.GestionHotelera.persistence.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${security.refreshToken.expiration-minutes}")
    private long refreshTokenExpirationMinutes;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    private RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Refresh-token no encontrado"));
    }

    public RefreshToken crearRefreshToken(Long idUsuario) {
        RefreshToken refreshToken = RefreshToken.builder()
                .usuario(usuarioRepository.findById(idUsuario).get())
                .expiryDate(Instant.now().plusMillis(refreshTokenExpirationMinutes * 60 * 1000))
                .token(UUID.randomUUID().toString())
                .build();

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public TokenRefreshResponse refrescarToken(TokenRefreshRequest tokenRefreshRequest) {
        String requestRefreshToken = tokenRefreshRequest.getRefreshToken();

        return refreshTokenRepository.findByToken(requestRefreshToken)
                .map(this::verificarExpiracion)
                .map(RefreshToken::getUsuario)
                .map(usuario -> {
                    String jwt = jwtService.generateToken(usuario);
                    return TokenRefreshResponse.builder()
                            .tokenType("Bearer")
                            .token(jwt)
                            .refreshToken(requestRefreshToken)
                            .build();
                }).orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh-token inexistente"));
    }

    private RefreshToken verificarExpiracion(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new TokenRefreshException(refreshToken.getToken(), "Refresh-token expirado. Inicie sesión nuevamente.");
        }
        return refreshToken;
    }

    private void deleteByUsuarioId(Long idUsuario) {
        refreshTokenRepository.deleteByUsuarioId(idUsuario);
    }

}
