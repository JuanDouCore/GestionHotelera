package ar.com.juanferrara.GestionHotelera.business.service;

import ar.com.juanferrara.GestionHotelera.domain.dto.CrearHabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.HabitacionDTO;

import java.util.List;

public interface HabitacionService {

    HabitacionDTO crearHabitacion(int idhotel, CrearHabitacionDTO crearHabitacionDTO);
    HabitacionDTO buscarHabitacionPorNroYHotel(int idhotel, int nroHabitacion);
    HabitacionDTO eliminarHabitacion(int idhotel, int nroHabitacion);
    HabitacionDTO modificarHabitacion(int idhotel, int nroHabitacion, CrearHabitacionDTO crearHabitacionDTO);
    boolean existeHabitacionPorNroYHotel(int idhotel, int nroHabitacion);
    List<HabitacionDTO> listarTodasHabitaciones();
    List<HabitacionDTO> listarTodasLasHabitacionesDeUnHotel(int idHotel);
    List<HabitacionDTO> listarTodasLasHabitacionesDeUnaCategoriaDeUnHotel(int idHotel, int idCategoria);
}
