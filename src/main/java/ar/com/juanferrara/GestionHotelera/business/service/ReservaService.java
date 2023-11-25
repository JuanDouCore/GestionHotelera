package ar.com.juanferrara.GestionHotelera.business.service;

import ar.com.juanferrara.GestionHotelera.domain.dto.*;

import java.util.Date;
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
    ReservaDTO confirmarComienzoEstadia(int idHotel, int id, double cantidadAbonada);
    HabitacionDTO obtenerHabitacionDisponibleSegunCriterio(int idHotel, CriterioReservaHabitacionDTO criterioReservaHabitacionDTO);
    List<ReservaDTO> listarReservasDeUnHotel(int idHotel);
    Map<Date, List<Integer>> obtenerHabitacionesDisponiblesEnRangoDeFecha(int idHotel, CriterioReservaHabitacionDTO criterioReservaHabitacionDTO, Date fechaMesYAnio);

}
