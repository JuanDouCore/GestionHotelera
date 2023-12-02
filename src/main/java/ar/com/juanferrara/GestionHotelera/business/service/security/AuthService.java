package ar.com.juanferrara.GestionHotelera.business.service.security;

import ar.com.juanferrara.GestionHotelera.business.mapper.impl.UsuarioMapper;
import ar.com.juanferrara.GestionHotelera.domain.dto.security.AuthenticationRequest;
import ar.com.juanferrara.GestionHotelera.domain.dto.security.AuthenticationResponse;
import ar.com.juanferrara.GestionHotelera.domain.entity.Usuario;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.NotFoundException;
import ar.com.juanferrara.GestionHotelera.persistence.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;
    
    @Autowired
    private UsuarioMapper usuarioMapper;


    public AuthenticationResponse login(AuthenticationRequest authenticationRequest){
        if(!usuarioRepository.existsByUsername(authenticationRequest.getUsername()))
            throw new NotFoundException("Usuario no encontrado");

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        Usuario usuario = usuarioRepository.findByUsername(authenticationRequest.getUsername()).get();
        
        String jwt = jwtService.generateToken(usuario);
        String refreshToken = refreshTokenService.crearRefreshToken(usuario.getId()).getToken();

        return AuthenticationResponse.builder()
                .usuario(usuarioMapper.toDto(usuario))
                .tokenType("Bearer")
                .token(jwt)
                .refreshToken(refreshToken)
                .build();
    }



}
