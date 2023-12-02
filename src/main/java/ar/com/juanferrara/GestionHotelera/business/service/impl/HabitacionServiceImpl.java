package ar.com.juanferrara.GestionHotelera.business.service.impl;

import ar.com.juanferrara.GestionHotelera.business.mapper.impl.CrearHabitacionMapper;
import ar.com.juanferrara.GestionHotelera.business.mapper.impl.HabitacionMapper;
import ar.com.juanferrara.GestionHotelera.business.service.CategoriaHabitacionService;
import ar.com.juanferrara.GestionHotelera.business.service.HabitacionService;
import ar.com.juanferrara.GestionHotelera.business.service.HotelService;
import ar.com.juanferrara.GestionHotelera.domain.dto.habitacion.CrearHabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.habitacion.HabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.Habitacion;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.HabitacionException;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.HotelException;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.NotFoundException;
import ar.com.juanferrara.GestionHotelera.persistence.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitacionServiceImpl implements HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private CategoriaHabitacionService categoriaHabitacionService;
    @Autowired
    private HotelService hotelService;

    @Autowired
    private HabitacionMapper habitacionMapper;
    @Autowired
    private CrearHabitacionMapper crearHabitacionMapper;


    @Override
    public HabitacionDTO crearHabitacion(int idhotel, CrearHabitacionDTO crearHabitacionDTO) {
        HabitacionDTO habitacionDTO = crearHabitacionMapper.toEntity(crearHabitacionDTO);
        habitacionDTO.setNumero(habitacionRepository.obtenerProximoNroParaHabitacionDeHotel(idhotel));
        habitacionDTO.setHotel(hotelService.buscarHotelPorId(idhotel));
        habitacionDTO.setCategoriaHabitacion(categoriaHabitacionService.buscarCategoriaPorId(crearHabitacionDTO.getIdCategoriaHabitacion()));

        Habitacion habitacion = habitacionMapper.toEntity(habitacionDTO);

        return habitacionMapper.toDto(habitacionRepository.save(habitacion));
    }

    @Override
    public HabitacionDTO buscarHabitacionPorNroYHotel(int idHotel, int nroHabitacion) {
        verificarSiExisteHotel(idHotel);

        Habitacion habitacion = habitacionRepository.findByNroAndHotel(idHotel, nroHabitacion)
                .orElseThrow(() -> new NotFoundException("No existe habitacion con este numero"));

        return habitacionMapper.toDto(habitacion);
    }

    @Override
    public int obtenerIdHabitacionPorNroYHotel(int idHotel, int nroHabitacion) {
        verificarSiExisteHotel(idHotel);

        Habitacion habitacion = habitacionRepository.findByNroAndHotel(idHotel, nroHabitacion)
                .orElseThrow(() -> new NotFoundException("No existe habitacion con este numero"));

        return habitacion.getId();
    }

    @Override
    public HabitacionDTO eliminarHabitacion(int idHotel, int idHabitacion) {
        verificarSiExisteHotel(idHotel);

        if(habitacionRepository.checkSiExistenReservasConUnaHabitacion(idHotel, idHabitacion))
            throw new HabitacionException("No se puede eliminar esta habitacion dado que posee reservas asociadas");

        Habitacion habitacion = habitacionRepository.findByNroAndHotel(idHotel, idHabitacion)
                .orElseThrow(() -> new NotFoundException("No existe habitacion con esta ID"));

        habitacionRepository.deleteByNroAndHotel(idHotel, idHabitacion);

        return habitacionMapper.toDto(habitacion);
    }

    @Override
    public HabitacionDTO modificarHabitacion(int idHotel, int nroHabitacion, CrearHabitacionDTO crearHabitacionDTO) {
        verificarSiExisteHotel(idHotel);

        Habitacion habitacion = habitacionRepository.findByNroAndHotel(idHotel, nroHabitacion)
                .orElseThrow(() -> new NotFoundException("No existe habitacion con este numero"));
        int idHabitacion = habitacion.getId();

        HabitacionDTO habitacionDTO = habitacionMapper.toDto(habitacion);
        crearHabitacionMapper.updateHabitacion(habitacionDTO, crearHabitacionMapper.toEntity(crearHabitacionDTO));

        if(crearHabitacionDTO.getIdCategoriaHabitacion() != 0)
            habitacionDTO.setCategoriaHabitacion(categoriaHabitacionService.buscarCategoriaPorId(crearHabitacionDTO.getIdCategoriaHabitacion()));

        habitacionMapper.updateEntity(habitacion, habitacionMapper.toEntity(habitacionDTO));
        habitacion.setId(idHabitacion);

        habitacionRepository.save(habitacion);

        return habitacionMapper.toDto(habitacion);
    }

    @Override
    public boolean existeHabitacionPorNroYHotel(int idHotel, int nroHabitacion) {
        verificarSiExisteHotel(idHotel);
        return habitacionRepository.existByNroAndHotel(idHotel, nroHabitacion);
    }

    @Override
    public List<HabitacionDTO> listarTodasHabitaciones() {
        return habitacionMapper.toDTOList(habitacionRepository.findAll());
    }

    @Override
    public List<HabitacionDTO> listarTodasLasHabitacionesDeUnHotel(int idHotel) {
        verificarSiExisteHotel(idHotel);
        return habitacionMapper.toDTOList(habitacionRepository.obtenerHabitacionesDeUnHotel(idHotel));
    }

    @Override
    public List<HabitacionDTO> listarTodasLasHabitacionesDeUnaCategoriaDeUnHotel(int idHotel, int idCategoria) {
        verificarSiExisteHotel(idHotel);
        return habitacionMapper.toDTOList(habitacionRepository.obtenerHabitacionesDeUnaCategoriaDeUnHotel(idHotel, idCategoria));
    }

    private void verificarSiExisteHotel(int idHotel) {
        if(!hotelService.existeHotelPorId(idHotel))
            throw new HotelException("No existe un hotel con esta ID");
    }
}
