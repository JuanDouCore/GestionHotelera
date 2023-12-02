package ar.com.juanferrara.GestionHotelera.business.mapper.impl;

import ar.com.juanferrara.GestionHotelera.business.mapper.IMapper;
import ar.com.juanferrara.GestionHotelera.domain.dto.habitacion.CrearHabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.habitacion.HabitacionDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CrearHabitacionMapper extends IMapper<HabitacionDTO, CrearHabitacionDTO> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "numero", source = "numero", conditionExpression = "java(source.getNumero() != 0)")
    @Mapping(target = "precio", source = "precio", conditionExpression = "java(source.getPrecio() != 0)")
    @Mapping(target = "camas", source = "camas", conditionExpression = "java(source.getCamas() != 0)")
    @Mapping(target = "capacidad", source = "capacidad", conditionExpression = "java(source.getCapacidad() != 0)")
    void updateHabitacion(@MappingTarget HabitacionDTO target, HabitacionDTO source);

}
