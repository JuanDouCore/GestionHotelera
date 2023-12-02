package ar.com.juanferrara.GestionHotelera.domain.dto.reservas;

import ar.com.juanferrara.GestionHotelera.domain.dto.categorias.CategoriaHabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.enums.EstadoReservacion;
import ar.com.juanferrara.GestionHotelera.domain.enums.TipoPension;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class InfoReservaDTO {

    private int id;
    private int idHotel;
    private int nroHabitacion;
    private CategoriaHabitacionDTO categoriaHabitacion;
    private EstadoReservacion estadoReservacion;
    private int dniCliente;
    private double costoTotal;
    private double cantidadAbonada;
    private Date fechaIngreso;
    private Date fechaEgreso;
    private TipoPension tipoPension;
}
