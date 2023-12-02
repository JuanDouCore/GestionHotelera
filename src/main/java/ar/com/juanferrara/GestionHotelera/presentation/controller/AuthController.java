package ar.com.juanferrara.GestionHotelera.presentation.controller;

import ar.com.juanferrara.GestionHotelera.business.service.security.AuthService;
import ar.com.juanferrara.GestionHotelera.business.service.security.RefreshTokenService;
import ar.com.juanferrara.GestionHotelera.domain.dto.security.AuthenticationRequest;
import ar.com.juanferrara.GestionHotelera.domain.dto.security.AuthenticationResponse;
import ar.com.juanferrara.GestionHotelera.domain.dto.security.TokenRefreshRequest;
import ar.com.juanferrara.GestionHotelera.domain.dto.security.TokenRefreshResponse;
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
public class AuthController {

    @Autowired
    private AuthService authenticationService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PreAuthorize("permitAll")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.login(authenticationRequest));
    }

    @PreAuthorize("permitAll")
    @PostMapping("/refreshtoken")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody @Valid TokenRefreshRequest tokenRefreshRequest) {
        return ResponseEntity.ok(refreshTokenService.refrescarToken(tokenRefreshRequest));
    }

}
