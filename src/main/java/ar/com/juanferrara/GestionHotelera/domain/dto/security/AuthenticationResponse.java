package ar.com.juanferrara.GestionHotelera.domain.dto.security;

import ar.com.juanferrara.GestionHotelera.domain.dto.usuarios.UsuarioDTO;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticationResponse {
    private UsuarioDTO usuario;
    private String tokenType = "Bearer";
    private String token;
    private String refreshToken;
}
