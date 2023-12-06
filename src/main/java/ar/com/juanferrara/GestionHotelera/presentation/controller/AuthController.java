package ar.com.juanferrara.GestionHotelera.presentation.controller;

import ar.com.juanferrara.GestionHotelera.business.service.security.AuthService;
import ar.com.juanferrara.GestionHotelera.business.service.security.RefreshTokenService;
import ar.com.juanferrara.GestionHotelera.domain.dto.security.AuthenticationRequest;
import ar.com.juanferrara.GestionHotelera.domain.dto.security.AuthenticationResponse;
import ar.com.juanferrara.GestionHotelera.domain.dto.security.TokenRefreshRequest;
import ar.com.juanferrara.GestionHotelera.domain.dto.security.TokenRefreshResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")

@Tag(name = "1. Auth", description = "API para la autenticación de usuarios")

public class AuthController {

    @Autowired
    private AuthService authenticationService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Operation(summary = "Autenticar un usuario", description = "Autentica un usuario por nombre de usuario y contraseña")
    @ApiResponse(responseCode = "200", description = "Usuario autenticado. Devuelve el token de acceso y el token de refresco")

    @PreAuthorize("permitAll")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.login(authenticationRequest));
    }

    @Operation(summary = "Refrescar el token de un usuario", description = "Refresca el token de un usuario por el token de refresco")
    @ApiResponse(responseCode = "200", description = "Token de acceso refrescado")

    @PreAuthorize("permitAll")
    @PostMapping("/refreshtoken")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody @Valid TokenRefreshRequest tokenRefreshRequest) {
        return ResponseEntity.ok(refreshTokenService.refrescarToken(tokenRefreshRequest));
    }

}
