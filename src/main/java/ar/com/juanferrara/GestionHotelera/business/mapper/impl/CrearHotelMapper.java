package ar.com.juanferrara.GestionHotelera.business.mapper.impl;

import ar.com.juanferrara.GestionHotelera.business.mapper.IMapper;
import ar.com.juanferrara.GestionHotelera.domain.dto.CrearHotelDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.HotelDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.CategoriaHotel;
import ar.com.juanferrara.GestionHotelera.domain.entity.Hotel;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CrearHotelMapper extends IMapper<HotelDTO, CrearHotelDTO> {
}
