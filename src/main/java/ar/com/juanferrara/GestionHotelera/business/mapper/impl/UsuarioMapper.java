package ar.com.juanferrara.GestionHotelera.business.mapper.impl;

import ar.com.juanferrara.GestionHotelera.domain.dto.usuarios.CrearUsuarioDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.usuarios.UsuarioDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UsuarioMapper {
    UsuarioDTO toDto(Usuario usuario);
    Usuario toUsuarioFromCrearUsuarioDto(CrearUsuarioDTO crearUsuarioDTO);
}
