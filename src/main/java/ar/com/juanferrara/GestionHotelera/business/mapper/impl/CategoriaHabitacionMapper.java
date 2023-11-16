package ar.com.juanferrara.GestionHotelera.business.mapper.impl;

import ar.com.juanferrara.GestionHotelera.business.mapper.IMapper;
import ar.com.juanferrara.GestionHotelera.domain.dto.CategoriaHabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.CategoriaHabitacion;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CategoriaHabitacionMapper extends IMapper<CategoriaHabitacion, CategoriaHabitacionDTO> {
}
