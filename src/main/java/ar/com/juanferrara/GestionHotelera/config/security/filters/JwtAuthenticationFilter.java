package ar.com.juanferrara.GestionHotelera.config.security.filters;

import ar.com.juanferrara.GestionHotelera.business.service.security.JwtService;
import ar.com.juanferrara.GestionHotelera.domain.entity.Usuario;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.NotFoundException;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.UsuarioException;
import ar.com.juanferrara.GestionHotelera.persistence.UsuarioRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.split(" ")[1];

        if (!isValidToken(jwt, response))
            return;

        String username = jwtService.extractUsername(jwt);

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioException("Usuario no encontrado"));
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    private boolean isValidToken(String jwt, HttpServletResponse response) throws IOException {
        boolean isValidToken = false;
        try {
            jwtService.validateToken(jwt);
            isValidToken = true;
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            String responseBody = "{\"unauthorized\": \"expired token\"}";

            catchResponseValidateException(response, responseBody);
        } catch (SignatureException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            String responseBody = "{\"unauthorized\": \"invalid sign token\"}";

            catchResponseValidateException(response, responseBody);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            String responseBody = "{\"unauthorized\": \"malformed token\"}";

            catchResponseValidateException(response, responseBody);
        }  catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            String responseBody = "{\"unauthorized\": \"" + e.getMessage() + "\"}";

            catchResponseValidateException(response, responseBody);
        }

        return isValidToken;
    }

    private void catchResponseValidateException(HttpServletResponse response, String responseBody) throws IOException {
        PrintWriter out = response.getWriter();
        out.write(responseBody);
        out.flush();
    }
}
