package ar.com.juanferrara.GestionHotelera.business.service;

import ar.com.juanferrara.GestionHotelera.domain.dto.hotel.CrearHotelDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.hotel.HotelDTO;

import java.util.List;

public interface HotelService {

    HotelDTO crearHotel(CrearHotelDTO crearHotelDTO);
    HotelDTO buscarHotelPorId(int id);
    HotelDTO eliminarHotel(int id);
    HotelDTO modificarHotel(int id, CrearHotelDTO crearHotelDTO);
    boolean existeHotelPorId(int id);
    List<HotelDTO> listarTodosHoteles();


}
