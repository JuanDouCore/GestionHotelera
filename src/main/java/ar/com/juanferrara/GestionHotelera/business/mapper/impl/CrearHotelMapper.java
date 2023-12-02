package ar.com.juanferrara.GestionHotelera.business.mapper.impl;

import ar.com.juanferrara.GestionHotelera.business.mapper.IMapper;
import ar.com.juanferrara.GestionHotelera.domain.dto.hotel.CrearHotelDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.hotel.HotelDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CrearHotelMapper extends IMapper<HotelDTO, CrearHotelDTO> {
}
