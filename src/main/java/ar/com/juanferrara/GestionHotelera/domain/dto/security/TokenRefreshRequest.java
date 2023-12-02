package ar.com.juanferrara.GestionHotelera.domain.dto.security;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}
