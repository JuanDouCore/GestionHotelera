package ar.com.juanferrara.GestionHotelera.domain.dto.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenRefreshResponse {
    private String tokenType = "Bearer";
    private String token;
    private String refreshToken;
}
