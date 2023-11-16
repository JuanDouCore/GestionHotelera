package ar.com.juanferrara.GestionHotelera.business.mapper.impl;

import ar.com.juanferrara.GestionHotelera.business.mapper.IMapper;
import ar.com.juanferrara.GestionHotelera.domain.dto.ClienteDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.HabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.Cliente;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ClienteMapper extends IMapper<Cliente, ClienteDTO> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "telefono", source = "telefono", conditionExpression = "java(source.getTelefono() != 0)")
    @Mapping(target = "direccion", ignore = true)
    void updateCliente(@MappingTarget Cliente target, Cliente source);

}
