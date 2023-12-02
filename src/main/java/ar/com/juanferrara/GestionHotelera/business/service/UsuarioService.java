package ar.com.juanferrara.GestionHotelera.business.service;

import ar.com.juanferrara.GestionHotelera.domain.dto.usuarios.CrearUsuarioDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.security.GerenteRequest;
import ar.com.juanferrara.GestionHotelera.domain.dto.usuarios.UsuarioDTO;
import ar.com.juanferrara.GestionHotelera.domain.enums.Role;

public interface UsuarioService {

    UsuarioDTO crearUsuario(CrearUsuarioDTO crearUsuarioDTO);
    UsuarioDTO eliminarUsuario(int dni);
    UsuarioDTO obtenerUsuarioPorDni(int dni);
    UsuarioDTO modificarContraseñaUsuario(int dni, String contraseña);
    UsuarioDTO cambiarHotelAsignadoUsuario(int dni, int idHotel);
    UsuarioDTO modificarRolUsuario(int dni, Role rol);
    UsuarioDTO asignarGerente(GerenteRequest gerenteRequest);
    UsuarioDTO eliminarGerente(GerenteRequest gerenteRequest);
}
