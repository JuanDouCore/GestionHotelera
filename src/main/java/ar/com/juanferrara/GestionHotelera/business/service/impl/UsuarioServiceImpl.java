package ar.com.juanferrara.GestionHotelera.business.service.impl;

import ar.com.juanferrara.GestionHotelera.business.mapper.impl.UsuarioMapper;
import ar.com.juanferrara.GestionHotelera.business.service.HotelService;
import ar.com.juanferrara.GestionHotelera.business.service.UsuarioService;
import ar.com.juanferrara.GestionHotelera.domain.dto.usuarios.CrearUsuarioDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.security.GerenteRequest;
import ar.com.juanferrara.GestionHotelera.domain.dto.usuarios.UsuarioDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.Usuario;
import ar.com.juanferrara.GestionHotelera.domain.enums.Role;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.UsuarioException;
import ar.com.juanferrara.GestionHotelera.persistence.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Value("${security.llave-maestra}")
    private String llaveMaestra;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private HotelService hotelService;

    @Override
    public UsuarioDTO crearUsuario(CrearUsuarioDTO crearUsuarioDTO) {
        if(usuarioRepository.existsByDniOrUsername(crearUsuarioDTO.getDni(), crearUsuarioDTO.getUsername()))
            throw new UsuarioException("Ya existe un usuario con este dni o nombre de usuario");

        if(!hotelService.existeHotelPorId(crearUsuarioDTO.getIdHotelAsignado()))
            throw new UsuarioException("No existe un hotel con este id");

        Usuario usuario = usuarioMapper.toUsuarioFromCrearUsuarioDto(crearUsuarioDTO);
        usuario.setNombreHotelAsignado(hotelService.buscarHotelPorId(crearUsuarioDTO.getIdHotelAsignado()).getNombre());
        usuario.setRole(Role.EMPLEADO);
        usuario.setPassword(passwordEncoder.encode(crearUsuarioDTO.getPassword()));

        return usuarioMapper.toDto(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDTO eliminarUsuario(int dni) {
        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new UsuarioException("No existe un usuario con este dni"));

        if(usuario.getRole().equals(Role.GERENTE))
            throw new UsuarioException("No se puede eliminar un gerente");

        usuarioRepository.delete(usuario);

        return usuarioMapper.toDto(usuario);
    }

    @Override
    public UsuarioDTO obtenerUsuarioPorDni(int dni) {
        return usuarioMapper.toDto(usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new UsuarioException("No existe un usuario con este dni")));
    }


    @Override
    public UsuarioDTO modificarContraseñaUsuario(int dni, String contraseña) {
        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new UsuarioException("No existe un usuario con este dni"));

        usuario.setPassword(passwordEncoder.encode(contraseña));

        return usuarioMapper.toDto(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDTO cambiarHotelAsignadoUsuario(int dni, int idHotel) {
        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new UsuarioException("No existe un usuario con este dni"));

        if(!hotelService.existeHotelPorId(idHotel))
            throw new UsuarioException("No existe un hotel con este id");

        usuario.setIdHotelAsignado(idHotel);
        usuario.setNombreHotelAsignado(hotelService.buscarHotelPorId(idHotel).getNombre());


        return usuarioMapper.toDto(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDTO modificarRolUsuario(int dni, Role rol) {
        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new UsuarioException("No existe un usuario con este dni"));

        if(rol.equals(Role.GERENTE))
            throw new UsuarioException("Para asignar un rol de gerente utiliza la ruta /usuarios/gerente/asignar");

        usuario.setRole(rol);

        return usuarioMapper.toDto(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDTO asignarGerente(GerenteRequest gerenteRequest) {
        Usuario usuario = usuarioRepository.findByDni(gerenteRequest.getDni())
                .orElseThrow(() -> new UsuarioException("No existe un usuario con este dni"));

        if(usuario.getRole().equals(Role.GERENTE))
            throw new UsuarioException("Este usuario ya es gerente");

        if (!gerenteRequest.getLlaveMaestra().equals(this.llaveMaestra))
            throw new UsuarioException("La llave maestra es incorrecta");

        usuario.setRole(Role.GERENTE);

        return usuarioMapper.toDto(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDTO eliminarGerente(GerenteRequest gerenteRequest) {
        Usuario usuario = usuarioRepository.findByDni(gerenteRequest.getDni())
                .orElseThrow(() -> new UsuarioException("No existe un usuario con este dni"));

        if(!usuario.getRole().equals(Role.GERENTE))
            throw new UsuarioException("Este usuario no es gerente");

        if (!gerenteRequest.getLlaveMaestra().equals(this.llaveMaestra))
            throw new UsuarioException("La llave maestra es incorrecta");

        usuario.setRole(Role.EMPLEADO);

        return usuarioMapper.toDto(usuarioRepository.save(usuario));
    }
}
