package ar.com.juanferrara.GestionHotelera.business.mapper.impl;

import ar.com.juanferrara.GestionHotelera.business.mapper.IMapper;
import ar.com.juanferrara.GestionHotelera.domain.dto.CrearReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.HabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.ReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.Reserva;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CrearReservaMapper extends IMapper<ReservaDTO, CrearReservaDTO> {


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cantidadAbonada", source = "cantidadAbonada", conditionExpression = "java(source.getCantidadAbonada() != 0)")
    void updateReserva(@MappingTarget ReservaDTO target, ReservaDTO source);
}
