package ar.com.juanferrara.GestionHotelera.business.mapper.impl;

import ar.com.juanferrara.GestionHotelera.domain.dto.reservas.InfoReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.Reserva;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface InfoReservaMapper {

    @Mapping(target = "idHotel", source = "reserva.hotel.id")
    @Mapping(target = "nroHabitacion", source = "reserva.habitacion.numero")
    @Mapping(target = "categoriaHabitacion", source = "reserva.habitacion.categoriaHabitacion")
    @Mapping(target = "dniCliente", source = "reserva.cliente.dni")
    InfoReservaDTO convertirADTO(Reserva reserva);

    @Mapping(target = "idHotel", source = "reserva.hotel.id")
    @Mapping(target = "nroHabitacion", source = "reserva.habitacion.numero")
    @Mapping(target = "dniCliente", source = "reserva.cliente.dni")
    List<InfoReservaDTO> convertirAListaInfoDTO(List<Reserva> entityList);
}
