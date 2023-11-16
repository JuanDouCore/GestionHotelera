package ar.com.juanferrara.GestionHotelera.business.service;

import ar.com.juanferrara.GestionHotelera.domain.dto.CrearHotelDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.CrearReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.CriterioReservaHabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.ReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.enums.EstadoReservacion;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ReservaService {

    //metodos generales de reservas
    ReservaDTO modificarReserva(int id, CrearReservaDTO crearReservaDTO);
    ReservaDTO buscarReservaPorId(int id);
    ReservaDTO eliminarReserva(int id);
    boolean existeReservaPorId(int id);
    List<ReservaDTO> listarTodasReservas();

    //metodos de reservas por hoteles
    ReservaDTO crearReserva(int idHotel, CrearReservaDTO crearReservaDTO);
    ReservaDTO cambiarEstadoReserva(int idHotel, int id, EstadoReservacion estadoReservacion);
    ReservaDTO confirmarComienzoDeAlojamiento(int idHotel, int id, double cantidadAbonada);
    ReservaDTO obtenerHabitacionDisponibleSegunCriterio(int idHotel, CriterioReservaHabitacionDTO criterioReservaHabitacionDTO);
    List<ReservaDTO> listarReservasDeUnHotel(int idHotel);
    Map<String, List<Integer>> obtenerHabitacionesDisponiblesEnRangoDeFecha(int idHotel, CriterioReservaHabitacionDTO criterioReservaHabitacionDTO);

}
