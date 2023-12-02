package ar.com.juanferrara.GestionHotelera.presentation.controller;

import ar.com.juanferrara.GestionHotelera.business.service.UsuarioService;
import ar.com.juanferrara.GestionHotelera.domain.dto.usuarios.CrearUsuarioDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.security.GerenteRequest;
import ar.com.juanferrara.GestionHotelera.domain.dto.usuarios.UsuarioDTO;
import ar.com.juanferrara.GestionHotelera.domain.enums.Role;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/usuarios")

@SecurityRequirement(name = "Bearer Authentication")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasRole('ADMINISTRADOR') || hasRole('GERENTE')")
    @GetMapping("{dni}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorDni(@PathVariable int dni) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorDni(dni));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR') || hasRole('GERENTE')")
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody CrearUsuarioDTO crearUsuarioDTO) {
        UsuarioDTO usuarioCreado = usuarioService.crearUsuario(crearUsuarioDTO);
        return ResponseEntity.created(URI.create("/api/usuarios/" + usuarioCreado.getDni())).body(usuarioCreado);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR') || hasRole('GERENTE')")
    @DeleteMapping("{dni}")
    public ResponseEntity<UsuarioDTO> eliminarUsuario(@PathVariable int dni) {
        return ResponseEntity.ok(usuarioService.eliminarUsuario(dni));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR') || hasRole('GERENTE')")
    @PatchMapping("{dni}/contraseña")
    public ResponseEntity<UsuarioDTO> modificarContraseñaUsuario(@PathVariable int dni, @RequestBody String contraseña) {
        return ResponseEntity.ok(usuarioService.modificarContraseñaUsuario(dni, contraseña));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR') || hasRole('GERENTE')")
    @PatchMapping("{dni}/hotel")
    public ResponseEntity<UsuarioDTO> cambiarHotelAsignadoUsuario(@PathVariable int dni, @RequestBody int idHotel) {
        return ResponseEntity.ok(usuarioService.cambiarHotelAsignadoUsuario(dni, idHotel));
    }

    @PreAuthorize("hasRole('GERENTE')")
    @PatchMapping("{dni}/rol")
    public ResponseEntity<UsuarioDTO> modificarRolUsuario(@PathVariable int dni, @RequestBody Role rol) {
        return ResponseEntity.ok(usuarioService.modificarRolUsuario(dni, rol));
    }

    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping("/gerente")
    public ResponseEntity<UsuarioDTO> asignarGerente(@RequestBody GerenteRequest gerenteRequest) {
        UsuarioDTO usuarioAsignado = usuarioService.asignarGerente(gerenteRequest);
        return ResponseEntity.created(URI.create("/api/usuarios/" + usuarioAsignado.getDni())).body(usuarioAsignado);
    }

    @PreAuthorize("hasRole('GERENTE')")
    @DeleteMapping("/gerente")
    public ResponseEntity<UsuarioDTO> eliminarGerente(@RequestBody GerenteRequest gerenteRequest) {
        return ResponseEntity.ok(usuarioService.eliminarGerente(gerenteRequest));
    }

}
