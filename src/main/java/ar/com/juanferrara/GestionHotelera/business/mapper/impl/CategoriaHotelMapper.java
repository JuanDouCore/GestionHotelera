package ar.com.juanferrara.GestionHotelera.business.mapper.impl;

import ar.com.juanferrara.GestionHotelera.business.mapper.IMapper;
import ar.com.juanferrara.GestionHotelera.domain.dto.CategoriaHotelDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.CategoriaHotel;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CategoriaHotelMapper extends IMapper<CategoriaHotel, CategoriaHotelDTO> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "estrellas", source = "estrellas", conditionExpression = "java(source.getEstrellas() != 0)")
    void updateCategoria(@MappingTarget CategoriaHotel target, CategoriaHotel source);
}
