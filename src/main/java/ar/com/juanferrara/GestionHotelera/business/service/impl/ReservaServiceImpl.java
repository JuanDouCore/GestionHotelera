package ar.com.juanferrara.GestionHotelera.business.service.impl;

import ar.com.juanferrara.GestionHotelera.business.mapper.impl.CrearReservaMapper;
import ar.com.juanferrara.GestionHotelera.business.mapper.impl.ReservaMapper;
import ar.com.juanferrara.GestionHotelera.business.service.ClienteService;
import ar.com.juanferrara.GestionHotelera.business.service.HabitacionService;
import ar.com.juanferrara.GestionHotelera.business.service.HotelService;
import ar.com.juanferrara.GestionHotelera.business.service.ReservaService;
import ar.com.juanferrara.GestionHotelera.domain.dto.CrearReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.CriterioReservaHabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.HabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.ReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.Reserva;
import ar.com.juanferrara.GestionHotelera.domain.enums.EstadoReservacion;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.NotFoundException;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.ReservaException;
import ar.com.juanferrara.GestionHotelera.persistence.ReservasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        if(reservasRepository.verificarSiHabitacionEstaDisponible(idHotel, crearReservaDTO.getNroHabitacion(), crearReservaDTO.getFechaIngreso(), crearReservaDTO.getFechaEgreso()))
            throw new ReservaException("La habitacion solicitada no esta disponible en estas fechas");

        ReservaDTO reservaDTO = crearReservaMapper.toEntity(crearReservaDTO);

        reservaDTO.setHotel(hotelService.buscarHotelPorId(idHotel));
        reservaDTO.setHabitacion(habitacionService.buscarHabitacionPorNroYHotel(idHotel, crearReservaDTO.getNroHabitacion()));
        reservaDTO.setCliente(clienteService.buscarClientePorDni(crearReservaDTO.getDniCliente()));
        reservaDTO.setCostoTotal(calcularCostoTotalReserva(reservaDTO.getHabitacion().getPrecio(), reservaDTO.getFechaIngreso(), reservaDTO.getFechaEgreso()));

        Reserva reserva = reservaMapper.toEntity(reservaDTO);
        reserva.getHabitacion().setId(habitacionService.obtenerIdHabitacionPorNroYHotel(idHotel, crearReservaDTO.getNroHabitacion()));

        return reservaMapper.toDto(reservasRepository.save(reserva));
    }

    @Override
    public ReservaDTO buscarReservaPorId(int id) {
        Reserva reserva = reservasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe reserva con esta ID"));

        return reservaMapper.toDto(reserva);
    }

    @Override
    public ReservaDTO eliminarReserva(int id) {
        Reserva reserva = reservasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe reserva con esta ID"));

        reservasRepository.deleteById(id);

        return reservaMapper.toDto(reserva);
    }

    @Override
    public ReservaDTO modificarReserva(int id, CrearReservaDTO crearReservaDTO) {
        Reserva reserva = reservasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe reserva con esta ID"));

        ReservaDTO reservaDTO = reservaMapper.toDto(reserva);

        crearReservaMapper.updateReserva(reservaDTO, crearReservaMapper.toEntity(crearReservaDTO));

        reservaDTO.setId(id);

        boolean huboAlteracionDeReserva = false;
        if(crearReservaDTO.getFechaIngreso() != null || crearReservaDTO.getFechaEgreso() != null) {
            Date fechaIngreso = (crearReservaDTO.getFechaIngreso() == null ? reserva.getFechaIngreso() : crearReservaDTO.getFechaIngreso());
            Date fechaEgreso = (crearReservaDTO.getFechaEgreso() == null ? reserva.getFechaEgreso() : crearReservaDTO.getFechaEgreso());

            if(!checkSiEsRangoFechaCoherente(fechaIngreso, fechaEgreso))
                throw new ReservaException("El rango de fechas ingresado es incorrecto");
            huboAlteracionDeReserva = true;
        }
        if(crearReservaDTO.getNroHabitacion() > 0) {
            reservaDTO.setHabitacion(habitacionService.buscarHabitacionPorNroYHotel(reservaDTO.getHotel().getId(), crearReservaDTO.getNroHabitacion()));
            huboAlteracionDeReserva = true;
        }
        if(crearReservaDTO.getDniCliente() > 0)
            reservaDTO.setCliente(clienteService.buscarClientePorDni(crearReservaDTO.getDniCliente()));

        if(huboAlteracionDeReserva) {
            if(!reservasRepository.verificarSiHabitacionEstaDisponible(reservaDTO.getHotel().getId(), reservaDTO.getHabitacion().getNumero(), reservaDTO.getFechaIngreso(), reservaDTO.getFechaEgreso()))
                throw new ReservaException("La habitacion solicitada no esta disponible en estas fechas");

            reservaDTO.setCostoTotal(calcularCostoTotalReserva(reservaDTO.getHabitacion().getPrecio(), reservaDTO.getFechaIngreso(), reservaDTO.getFechaEgreso()));
        }

        reservaMapper.updateEntity(reserva, reservaMapper.toEntity(reservaDTO));

        reserva.getHabitacion().setId(habitacionService.obtenerIdHabitacionPorNroYHotel(reservaDTO.getHotel().getId(), reservaDTO.getHabitacion().getNumero()));

        reservasRepository.save(reserva);

        return reservaMapper.toDto(reserva);
    }

    @Override
    public ReservaDTO confirmarComienzoEstadia(int idHotel, int id, double cantidadAbonada) {
        Reserva reserva = reservasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe reserva con esta ID"));

        reserva.setEstadoReservacion(EstadoReservacion.CONFIRMADO);
        reserva.setCantidadAbonada(cantidadAbonada);

        reservasRepository.save(reserva);

        return reservaMapper.toDto(reserva);
    }


    @Override
    public HabitacionDTO obtenerHabitacionDisponibleSegunCriterio(int idHotel, CriterioReservaHabitacionDTO criterioReservaHabitacionDTO) {
        if(!checkSiEsRangoFechaCoherente(criterioReservaHabitacionDTO.getFechaInicio(), criterioReservaHabitacionDTO.getFechaFin()))
            throw new ReservaException("El rango de fechas ingresado es incorrecto");

        List<HabitacionDTO> habitacionesSegunElCriterio = habitacionService.listarTodasLasHabitacionesDeUnHotel(idHotel)
                .stream()
                .filter(habitacionDTO -> habitacionCompatibleConCriterio(habitacionDTO, criterioReservaHabitacionDTO))
                .toList();

        List<Reserva> reservasEnRango = reservasRepository
                .listarReservasPorHotelIdAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                        idHotel, criterioReservaHabitacionDTO.getFechaInicio(), criterioReservaHabitacionDTO.getFechaFin());

        return habitacionesSegunElCriterio.stream()
                .filter(habitacionDTO -> esHabitacionDisponibleEnRango(habitacionDTO, reservasEnRango))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No existen habitaciones disponibles"));
    }


    @Override
    public boolean existeReservaPorId(int id) {
        return reservasRepository.existsById(id);
    }

    @Override
    public List<ReservaDTO> listarTodasReservas() {
        return reservaMapper.toDTOList(reservasRepository.findAll());
    }

    @Override
    public List<ReservaDTO> listarReservasDeUnHotel(int idHotel) {
        return reservaMapper.toDTOList(reservasRepository.listarReservasDeHotel(idHotel));
    }

    public Map<Date, List<Integer>> obtenerHabitacionesDisponiblesEnRangoDeFecha(int idHotel, CriterioReservaHabitacionDTO criterioReservaHabitacionDTO, Date fechaMesYAnio) {
        LocalDate fechaInicioMes = fechaMesYAnio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaFinMes = fechaInicioMes.plusMonths(1).minusDays(1);

        List<Reserva> reservasEnRango = reservasRepository.listarReservasPorHotelIdAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                idHotel, Date.from(fechaInicioMes.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(fechaFinMes.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        List<HabitacionDTO> habitaciones = new ArrayList<>(habitacionService.listarTodasLasHabitacionesDeUnHotel(idHotel)
                .stream()
                .filter(habitacionDTO -> habitacionCompatibleConCriterio(habitacionDTO, criterioReservaHabitacionDTO))
                .toList());

        Map<Date, List<Integer>> habitacionesDisponiblesPorFecha = new TreeMap<>();

        List<LocalDate> fechasEnMes = IntStream.range(0, fechaInicioMes.until(fechaFinMes).getDays())
                .mapToObj(fechaInicioMes::plusDays)
                .toList();


        return fechasEnMes.stream()
                .collect(Collectors.toMap(
                        fecha -> Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        fecha -> obtenerNroHabitacionesDisponiblesEnFecha(idHotel, habitaciones, reservasEnRango, Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant())),
                        (existing, replacement) -> existing,
                        TreeMap::new));
    }



    private boolean checkSiEsRangoFechaCoherente(Date fechaInicio, Date fechaFin) {
        LocalDate fechaInicioLocalDate = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaFinLocalDate = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate hoy = LocalDate.now();

        return (fechaFinLocalDate.isAfter(fechaInicioLocalDate) || fechaFinLocalDate.minusDays(1).isEqual(fechaInicioLocalDate))
                && !fechaInicioLocalDate.isBefore(hoy);
    }

    private double calcularCostoTotalReserva(double precioHabitacion, Date fechaInicio, Date fechaFin) {
        LocalDate fechaInicioLocalDate = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaFinLocalDate = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long diferenciaDias = ChronoUnit.DAYS.between(fechaInicioLocalDate, fechaFinLocalDate);

        return (precioHabitacion*diferenciaDias);
    }

    private boolean habitacionCompatibleConCriterio(HabitacionDTO habitacionDTO, CriterioReservaHabitacionDTO criterioReservaHabitacionDTO) {
        return (habitacionDTO.getCapacidad() >= criterioReservaHabitacionDTO.getOcupantes() &&
            habitacionDTO.getCamas() >= criterioReservaHabitacionDTO.getCamas() &&
                habitacionDTO.getCategoriaHabitacion().getId() == criterioReservaHabitacionDTO.getCategoriaHabitacion());
    }

    private boolean esHabitacionDisponibleEnRango(HabitacionDTO habitacion, List<Reserva> reservasEnRango) {
        return reservasEnRango.stream()
                .noneMatch(reserva -> reserva.getHabitacion().getNumero() == habitacion.getNumero());
    }

    private List<Integer> obtenerNroHabitacionesDisponiblesEnFecha(int idHotel, List<HabitacionDTO> habitacionesDelHotel, List<Reserva> reservasEnRango, Date fecha) {
        List<Integer> numeroHabitaciones = new ArrayList<>(habitacionesDelHotel.stream()
                .map(HabitacionDTO::getNumero)
                .toList());

        List<Integer> habitacionesReservadas = reservasEnRango.stream()
                .filter(reserva -> {
                    Date fechaIngreso = reserva.getFechaIngreso();
                    Date fechaEgreso = reserva.getFechaEgreso();
                    return !fecha.before(fechaIngreso) && !fecha.after(fechaEgreso);
                })
                .map(reserva -> reserva.getHabitacion().getNumero())
                .toList();

        numeroHabitaciones.removeAll(habitacionesReservadas);

        return numeroHabitaciones;
    }


}
