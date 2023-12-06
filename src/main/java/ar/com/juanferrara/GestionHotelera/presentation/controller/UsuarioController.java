package ar.com.juanferrara.GestionHotelera.presentation.controller;

import ar.com.juanferrara.GestionHotelera.business.service.UsuarioService;
import ar.com.juanferrara.GestionHotelera.domain.dto.usuarios.CrearUsuarioDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.security.GerenteRequest;
import ar.com.juanferrara.GestionHotelera.domain.dto.usuarios.UsuarioDTO;
import ar.com.juanferrara.GestionHotelera.domain.enums.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/usuarios")

@Tag(name = "2. Usuario", description = "API para la gestión de usuarios")

@SecurityRequirement(name = "Bearer Authentication")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Obtener un usuario por dni", description = "Obtiene un usuario por dni")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")

    @PreAuthorize("hasRole('ADMINISTRADOR') || hasRole('GERENTE')")
    @GetMapping("{dni}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorDni(@PathVariable int dni) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorDni(dni));
    }

    @Operation(summary = "Crear un usuario", description = "Crea un usuario como empleado con un hotel asignado")
    @ApiResponse(responseCode = "201", description = "Usuario creado")

    @PreAuthorize("hasRole('ADMINISTRADOR') || hasRole('GERENTE')")
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody CrearUsuarioDTO crearUsuarioDTO) {
        UsuarioDTO usuarioCreado = usuarioService.crearUsuario(crearUsuarioDTO);
        return ResponseEntity.created(URI.create("/api/usuarios/" + usuarioCreado.getDni())).body(usuarioCreado);
    }

    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario por dni")
    @ApiResponse(responseCode = "200", description = "Usuario eliminado")

    @PreAuthorize("hasRole('ADMINISTRADOR') || hasRole('GERENTE')")
    @DeleteMapping("{dni}")
    public ResponseEntity<UsuarioDTO> eliminarUsuario(@PathVariable int dni) {
        return ResponseEntity.ok(usuarioService.eliminarUsuario(dni));
    }

    @Operation(summary = "Modificar la contraseña de un usuario", description = "Modifica la contraseña de un usuario por dni")
    @ApiResponse(responseCode = "200", description = "Contraseña modificada")

    @PreAuthorize("hasRole('ADMINISTRADOR') || hasRole('GERENTE')")
    @PatchMapping("{dni}/contraseña")
    public ResponseEntity<UsuarioDTO> modificarContraseñaUsuario(@PathVariable int dni, @RequestBody String contraseña) {
        return ResponseEntity.ok(usuarioService.modificarContraseñaUsuario(dni, contraseña));
    }

    @Operation(summary = "Modificar el hotel asignado a un usuario", description = "Modifica el hotel asignado a un usuario por dni")
    @ApiResponse(responseCode = "200", description = "Hotel asignado modificado")

    @PreAuthorize("hasRole('ADMINISTRADOR') || hasRole('GERENTE')")
    @PatchMapping("{dni}/hotel")
    public ResponseEntity<UsuarioDTO> cambiarHotelAsignadoUsuario(@PathVariable int dni, @RequestBody int idHotel) {
        return ResponseEntity.ok(usuarioService.cambiarHotelAsignadoUsuario(dni, idHotel));
    }

    @Operation(summary = "Modificar el rol de un usuario", description = "Modifica el rol de un usuario por dni")
    @ApiResponse(responseCode = "200", description = "Rol modificado")

    @PreAuthorize("hasRole('GERENTE')")
    @PatchMapping("{dni}/rol")
    public ResponseEntity<UsuarioDTO> modificarRolUsuario(@PathVariable int dni, @RequestBody Role rol) {
        return ResponseEntity.ok(usuarioService.modificarRolUsuario(dni, rol));
    }

    @Operation(summary = "Asignar un gerente", description = "Asigna un gerente")
    @ApiResponse(responseCode = "201", description = "Gerente asignado")

    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping("/gerente")
    public ResponseEntity<UsuarioDTO> asignarGerente(@RequestBody GerenteRequest gerenteRequest) {
        UsuarioDTO usuarioAsignado = usuarioService.asignarGerente(gerenteRequest);
        return ResponseEntity.created(URI.create("/api/usuarios/" + usuarioAsignado.getDni())).body(usuarioAsignado);
    }

    @Operation(summary = "Eliminar un gerente", description = "Elimina un gerente")
    @ApiResponse(responseCode = "200", description = "Gerente eliminado")

    @PreAuthorize("hasRole('GERENTE')")
    @DeleteMapping("/gerente")
    public ResponseEntity<UsuarioDTO> eliminarGerente(@RequestBody GerenteRequest gerenteRequest) {
        return ResponseEntity.ok(usuarioService.eliminarGerente(gerenteRequest));
    }

}
