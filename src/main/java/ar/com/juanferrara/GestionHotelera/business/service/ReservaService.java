package ar.com.juanferrara.GestionHotelera.business.service;

import ar.com.juanferrara.GestionHotelera.domain.dto.habitacion.HabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.reservas.CrearReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.reservas.CriterioReservaHabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.reservas.InfoReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.reservas.ReservaDTO;

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
    InfoReservaDTO crearReserva(int idHotel, CrearReservaDTO crearReservaDTO);
    InfoReservaDTO confirmarComienzoEstadia(int idHotel, int id, double cantidadAbonada);
    HabitacionDTO obtenerHabitacionDisponibleSegunCriterio(int idHotel, CriterioReservaHabitacionDTO criterioReservaHabitacionDTO);
    List<InfoReservaDTO> listarReservasDeUnHotel(int idHotel);
    Map<Date, List<Integer>> obtenerHabitacionesDisponiblesEnRangoDeFecha(int idHotel, CriterioReservaHabitacionDTO criterioReservaHabitacionDTO, Date fechaMesYAnio);
    List<InfoReservaDTO> obtenerReservasDeUnCliente(int dniCliente);

}
