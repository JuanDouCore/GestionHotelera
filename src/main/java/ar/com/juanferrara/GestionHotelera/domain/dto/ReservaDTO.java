package ar.com.juanferrara.GestionHotelera.domain.dto;

import ar.com.juanferrara.GestionHotelera.domain.enums.EstadoReservacion;
import ar.com.juanferrara.GestionHotelera.domain.enums.TipoPension;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ReservaDTO {

    private int id;

    private HotelDTO hotel;

    private HabitacionDTO habitacion;

    private ClienteDTO cliente;

    private EstadoReservacion estadoReservacion;

    @Positive
    private double costoTotal;

    @PositiveOrZero
    private double cantidadAbonada;

    private Date fechaIngreso;

    private Date fechaEgreso;

    private TipoPension tipoPension;
}
