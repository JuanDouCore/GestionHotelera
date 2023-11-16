package ar.com.juanferrara.GestionHotelera.business.service.impl;

import ar.com.juanferrara.GestionHotelera.business.mapper.impl.CrearReservaMapper;
import ar.com.juanferrara.GestionHotelera.business.mapper.impl.ReservaMapper;
import ar.com.juanferrara.GestionHotelera.business.service.ClienteService;
import ar.com.juanferrara.GestionHotelera.business.service.HabitacionService;
import ar.com.juanferrara.GestionHotelera.business.service.HotelService;
import ar.com.juanferrara.GestionHotelera.business.service.ReservaService;
import ar.com.juanferrara.GestionHotelera.domain.dto.CrearReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.CriterioReservaHabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.ReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.Reserva;
import ar.com.juanferrara.GestionHotelera.domain.enums.EstadoReservacion;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.ReservaException;
import ar.com.juanferrara.GestionHotelera.persistence.ReservasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservasRepository reservasRepository;

    @Autowired
    private ReservaMapper reservaMapper;
    @Autowired
    private CrearReservaMapper crearReservaMapper;

    @Autowired
    private HotelService hotelService;
    @Autowired
    private HabitacionService habitacionService;
    @Autowired
    private ClienteService clienteService;


    @Override
    public ReservaDTO crearReserva(int idHotel, CrearReservaDTO crearReservaDTO) {
        if (!checkSiEsRangoFechaCoherente(crearReservaDTO.getFechaIngreso(), crearReservaDTO.getFechaEgreso()))
            throw new ReservaException("El rango de fechas ingresado es incorrecto");

        if(!reservasRepository.verificarSiHabitacionEstaDisponible(idHotel, crearReservaDTO.getNroHabitacion(), crearReservaDTO.getFechaIngreso(), crearReservaDTO.getFechaEgreso()))
            throw new ReservaException("La habitacion solicitada no esta disponible en estas fechas");

        ReservaDTO reservaDTO = crearReservaMapper.toEntity(crearReservaDTO);

        reservaDTO.setHotel(hotelService.buscarHotelPorId(idHotel));
        reservaDTO.setHabitacion(habitacionService.buscarHabitacionPorNroYHotel(idHotel, crearReservaDTO.getNroHabitacion()));
        reservaDTO.setCliente(clienteService.buscarClientePorDni(crearReservaDTO.getDniCliente()));
        reservaDTO.setCostoTotal(calcularCostoTotalReserva(reservaDTO.getHabitacion().getPrecio(), reservaDTO.getFechaIngreso(), reservaDTO.getFechaEgreso()));

        Reserva reserva = reservaMapper.toEntity(reservaDTO);

        return reservaMapper.toDto(reservasRepository.save(reserva));
        //done
    }

    @Override
    public ReservaDTO buscarReservaPorId(int id) {
        return null;
    }

    @Override
    public ReservaDTO eliminarReserva(int id) {
        return null;
    }

    @Override
    public ReservaDTO modificarReserva(int id, CrearReservaDTO crearReservaDTO) {
        return null;
    }

    @Override
    public ReservaDTO cambiarEstadoReserva(int idHotel, int id, EstadoReservacion estadoReservacion) {
        return null;
    }

    @Override
    public ReservaDTO confirmarComienzoDeAlojamiento(int idHotel, int id, double cantidadAbonada) {
        return null;
    }

    @Override
    public ReservaDTO obtenerHabitacionDisponibleSegunCriterio(int idHotel, CriterioReservaHabitacionDTO criterioReservaHabitacionDTO) {
        return null;
    }

    @Override
    public boolean existeReservaPorId(int id) {
        return false;
    }

    @Override
    public List<ReservaDTO> listarTodasReservas() {
        return null;
    }

    @Override
    public List<ReservaDTO> listarReservasDeUnHotel(int idHotel) {
        return null;
    }

    @Override
    public Map<String, List<Integer>> obtenerHabitacionesDisponiblesEnRangoDeFecha(int idHotel, CriterioReservaHabitacionDTO criterioReservaHabitacionDTO) {
        return null;
    }



    private boolean checkSiEsRangoFechaCoherente(Date fechaInicio, Date fechaFin) {
        LocalDate fechaInicioLocalDate = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaFinLocalDate = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //calculad
        return (fechaFinLocalDate.isAfter(fechaInicioLocalDate) && fechaFinLocalDate.minusDays(1).isAfter(fechaInicioLocalDate));
    }

    private double calcularCostoTotalReserva(double precioHabitacion, Date fechaInicio, Date fechaFin) {
        LocalDate fechaInicioLocalDate = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaFinLocalDate = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long diferenciaDias = ChronoUnit.DAYS.between(fechaFinLocalDate, fechaFinLocalDate);

        return (precioHabitacion*diferenciaDias);
    }
}
