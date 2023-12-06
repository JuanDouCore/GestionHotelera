package ar.com.juanferrara.GestionHotelera.business.service.impl;

import ar.com.juanferrara.GestionHotelera.business.mapper.impl.*;
import ar.com.juanferrara.GestionHotelera.business.service.ClienteService;
import ar.com.juanferrara.GestionHotelera.business.service.HabitacionService;
import ar.com.juanferrara.GestionHotelera.business.service.HotelService;
import ar.com.juanferrara.GestionHotelera.domain.dto.categorias.CategoriaHabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.categorias.CategoriaHotelDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.generic.DireccionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.habitacion.HabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.hotel.HotelDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.reservas.CrearReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.reservas.InfoReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.reservas.ReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.usuarios.ClienteDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.Reserva;
import ar.com.juanferrara.GestionHotelera.domain.enums.EstadoReservacion;
import ar.com.juanferrara.GestionHotelera.domain.enums.TipoPension;
import ar.com.juanferrara.GestionHotelera.persistence.ReservasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservaServiceImplTest {

    @InjectMocks
    private ReservaServiceImpl reservaService;

    @Mock
    ReservasRepository reservasRepository;

    @Mock
    private HotelService hotelService;
    @Mock
    private HabitacionService habitacionService;
    @Mock
    private ClienteService clienteService;

    @Spy
    private ReservaMapper reservaMapper = new ReservaMapperImpl();
    @Spy
    private CrearReservaMapper crearReservaMapper = new CrearReservaMapperImpl();
    @Spy
    private InfoReservaMapper infoReservaMapper = new InfoReservaMapperImpl();

    private ReservaDTO reservaDTO;
    private CrearReservaDTO crearReservaDTO;
    private Date fechaIngreso = null;
    private Date fechaEgreso = null;
    private HotelDTO hotel;
    private HabitacionDTO habitacion;
    private ClienteDTO cliente;
    private DireccionDTO direccionDTO;

    @BeforeEach
    void setup() {
        direccionDTO = DireccionDTO.builder()
                .direccion("sunchales 5703")
                .provincia("gonzalez catan")
                .localidad("bsas")
                .build();

        hotel = HotelDTO.builder()
                .id(1)
                .nombre("gran alvear")
                .categoriaHotel(CategoriaHotelDTO.builder()
                        .id(1)
                        .nombre("lujo")
                        .descripcion("hoteles de lujo")
                        .estrellas(5)
                        .build())
                .direccion(direccionDTO)
                .build();

        habitacion = HabitacionDTO.builder()
                .hotel(hotel)
                .numero(1)
                .camas(2)
                .capacidad(4)
                .precio(200.0)
                .categoriaHabitacion(
                        CategoriaHabitacionDTO.builder()
                                .id(1)
                                .nombre("grandes habitaciones")
                                .descripcion("habitaciones de lujo")
                                .build()
                )
                .build();

        cliente = ClienteDTO.builder()
                .dni(44560065)
                .direccion(direccionDTO)
                .nombre("juan jose")
                .apellido("ferrara")
                .telefono(1128841217)
                .email("jaja@gmail.com")
                .build();

        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            fechaIngreso = formato.parse("2023-12-15");
            fechaEgreso = formato.parse("2023-12-20");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        reservaDTO = ReservaDTO.builder()
                .id(1)
                .hotel(hotel)
                .habitacion(habitacion)
                .cliente(cliente)
                .cantidadAbonada(100.0)
                .costoTotal(1000.0)
                .fechaIngreso(fechaIngreso)
                .fechaEgreso(fechaEgreso)
                .estadoReservacion(EstadoReservacion.RESERVADO)
                .tipoPension(TipoPension.MEDIA)
                .build();
        
        crearReservaDTO = CrearReservaDTO.builder()
                .nroHabitacion(1)
                .cantidadAbonada(100.0)
                .dniCliente(44560065)
                .fechaIngreso(fechaIngreso)
                .fechaEgreso(fechaEgreso)
                .estadoReservacion(EstadoReservacion.RESERVADO)
                .tipoPension(TipoPension.MEDIA)
                .build();
    }



    @Test
    void crearReserva() {
        Reserva reserva = reservaMapper.toEntity(reservaDTO);
        when(reservasRepository.verificarSiHabitacionEstaDisponible(1, 1, fechaIngreso, fechaEgreso)).thenReturn(false);
        when(hotelService.buscarHotelPorId(1)).thenReturn(hotel);
        when(habitacionService.buscarHabitacionPorNroYHotel(1, 1)).thenReturn(habitacion);
        when(clienteService.buscarClientePorDni(44560065)).thenReturn(cliente);
        when(reservasRepository.save(any(Reserva.class))).thenReturn(reserva);

        InfoReservaDTO reservaCreada = reservaService.crearReserva(1, crearReservaDTO);

        verify(reservasRepository).verificarSiHabitacionEstaDisponible(1, 1, fechaIngreso, fechaEgreso);
        verify(hotelService).buscarHotelPorId(1);
        verify(habitacionService).buscarHabitacionPorNroYHotel(1, 1);
        verify(clienteService).buscarClientePorDni(44560065);
        verify(reservasRepository).save(any(Reserva.class));
        assertAll(() -> {
           assertEquals(1, reservaCreada.getId());
           assertEquals(cliente.getDni(), reservaCreada.getDniCliente());
           assertEquals(hotel.getId(), reservaCreada.getIdHotel());
           assertEquals(1000, reservaCreada.getCostoTotal());
        });
    }

    @Test
    void modificarReservaFechas() {
        Date fechaEgresoSecondary = null;
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            fechaEgresoSecondary = formato.parse("2023-12-25");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CrearReservaDTO crearReservaDTOSecondary = CrearReservaDTO.builder()
                .fechaEgreso(fechaEgresoSecondary)
                .tipoPension(TipoPension.COMPLETA)
                .build();
        Reserva reserva = reservaMapper.toEntity(reservaDTO);
        when(reservasRepository.findById(1)).thenReturn(Optional.ofNullable(reserva));
        when(reservasRepository.verificarSiHabitacionEstaDisponible(1, 1, fechaIngreso, fechaEgresoSecondary)).thenReturn(true);
        when(reservasRepository.save(any(Reserva.class))).thenReturn(reserva);

        ReservaDTO reservaModificada = reservaService.modificarReserva(1, crearReservaDTOSecondary);

        verify(reservasRepository).findById(1);
        verify(reservasRepository).verificarSiHabitacionEstaDisponible(1, 1, fechaIngreso, fechaEgresoSecondary);
        verify(reservasRepository).save(any(Reserva.class));
        Date finalFechaEgresoSecondary = fechaEgresoSecondary;
        assertAll(() -> {
            assertEquals(1, reservaModificada.getId());
            assertEquals(TipoPension.COMPLETA, reserva.getTipoPension());
            assertEquals(finalFechaEgresoSecondary, reservaModificada.getFechaEgreso());
            assertEquals(fechaIngreso, reservaModificada.getFechaIngreso());
            assertEquals(2000, reservaModificada.getCostoTotal());
        });
    }

    @Test
    void modificarReservaHabitacion() {
        HabitacionDTO habitacionDTOSecondary = HabitacionDTO.builder()
                .hotel(hotel)
                .numero(2)
                .categoriaHabitacion(habitacion.getCategoriaHabitacion())
                .precio(500.0)
                .camas(1)
                .capacidad(4)
                .build();
        CrearReservaDTO crearReservaDTOSecondary = CrearReservaDTO.builder()
                .nroHabitacion(2)
                .build();
        Reserva reserva = reservaMapper.toEntity(reservaDTO);
        when(reservasRepository.findById(1)).thenReturn(Optional.ofNullable(reserva));
        when(habitacionService.buscarHabitacionPorNroYHotel(1, 2)).thenReturn(habitacionDTOSecondary);
        when(reservasRepository.verificarSiHabitacionEstaDisponible(1, 2, fechaIngreso, fechaEgreso)).thenReturn(true);
        when(reservasRepository.save(any(Reserva.class))).thenReturn(reserva);

        ReservaDTO reservaModificada = reservaService.modificarReserva(1, crearReservaDTOSecondary);

        verify(reservasRepository).findById(1);
        verify(reservasRepository).verificarSiHabitacionEstaDisponible(1, 2, fechaIngreso, fechaEgreso);
        verify(reservasRepository).save(any(Reserva.class));
        assertAll(() -> {
            assertEquals(1, reservaModificada.getId());
            assertEquals(habitacionDTOSecondary, reservaModificada.getHabitacion());
            assertEquals(2500, reservaModificada.getCostoTotal());
        });
    }

    @Test
    void modificarReservaCliente() {
        ClienteDTO clienteDTOSecondary = ClienteDTO.builder()
                .dni(45000123)
                .nombre("Jorge")
                .apellido("Lopez")
                .direccion(direccionDTO)
                .email("jorgito@gmail.com")
                .telefono(11223311)
                .build();
        CrearReservaDTO crearReservaDTOSecondary = CrearReservaDTO.builder()
                .dniCliente(45000123)
                .build();
        Reserva reserva = reservaMapper.toEntity(reservaDTO);
        when(reservasRepository.findById(1)).thenReturn(Optional.ofNullable(reserva));
        when(clienteService.buscarClientePorDni(45000123)).thenReturn(clienteDTOSecondary);
        when(reservasRepository.save(any(Reserva.class))).thenReturn(reserva);

        ReservaDTO reservaModificada = reservaService.modificarReserva(1, crearReservaDTOSecondary);

        verify(reservasRepository).findById(1);
        verify(clienteService).buscarClientePorDni(45000123);
        verify(reservasRepository).save(any(Reserva.class));
        assertAll(() -> {
            assertEquals(1, reservaModificada.getId());
            assertEquals(clienteDTOSecondary, reservaModificada.getCliente());
        });
    }

    @Test
    void confirmarnComienzoEstadia() {

    }
}