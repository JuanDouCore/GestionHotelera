package ar.com.juanferrara.GestionHotelera.business.service.impl;

import ar.com.juanferrara.GestionHotelera.business.mapper.impl.CrearHabitacionMapper;
import ar.com.juanferrara.GestionHotelera.business.mapper.impl.CrearHabitacionMapperImpl;
import ar.com.juanferrara.GestionHotelera.business.mapper.impl.HabitacionMapper;
import ar.com.juanferrara.GestionHotelera.business.mapper.impl.HabitacionMapperImpl;
import ar.com.juanferrara.GestionHotelera.business.service.CategoriaHabitacionService;
import ar.com.juanferrara.GestionHotelera.business.service.HabitacionService;
import ar.com.juanferrara.GestionHotelera.business.service.HotelService;
import ar.com.juanferrara.GestionHotelera.domain.dto.*;
import ar.com.juanferrara.GestionHotelera.domain.entity.Habitacion;
import ar.com.juanferrara.GestionHotelera.persistence.HabitacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HabitacionServiceImplTest {
    /*

    @InjectMocks
    private HabitacionServiceImpl habitacionService;

    @Mock
    private HabitacionRepository habitacionRepository;

    @Mock
    private HotelService hotelService;
    @Mock
    private CategoriaHabitacionService categoriaHabitacionService;

    @Spy
    private HabitacionMapper habitacionMapper = new HabitacionMapperImpl();
    @Spy
    private CrearHabitacionMapper crearHabitacionMapper = new CrearHabitacionMapperImpl();

    private HabitacionDTO habitacionDTO;
    private CrearHabitacionDTO crearHabitacionDTO;
    private HotelDTO hotelDTO;
    private CategoriaHabitacionDTO categoriaHabitacionDTO;

    @BeforeEach
    void setup() {
        hotelDTO = HotelDTO.builder()
                .id(1)
                .nombre("alvear")
                .direccion(DireccionDTO.builder()
                        .id(1)
                        .direccion("alvear 321")
                        .localidad("ciudad bsas")
                        .provincia("bs as")
                        .build())
                .categoriaHotel(CategoriaHotelDTO.builder()
                        .id(1)
                        .nombre("Lujo")
                        .estrellas(5)
                        .descripcion("gran hoteles de lujo")
                        .build())
                .build();

        categoriaHabitacionDTO = CategoriaHabitacionDTO.builder()
                .id(1)
                .nombre("habitacion simple")
                .descripcion("habitaciones simples de lujo")
                .build();

        habitacionDTO = HabitacionDTO.builder()
                .id(1)
                .hotel(hotelDTO)
                .categoriaHabitacion(categoriaHabitacionDTO)
                .camas(2)
                .capacidad(2)
                .precio(200.0)
                .numero(1)
                .build();

        crearHabitacionDTO = CrearHabitacionDTO.builder()
                .idHotel(1)
                .idCategoriaHabitacion(1)
                .camas(2)
                .capacidad(2)
                .precio(200.0)
                .numero(1)
                .build();
    }

    @Test
    void testCrearHabitacion() {
        Habitacion habitacion = habitacionMapper.toEntity(habitacionDTO);
        when(hotelService.buscarHotelPorId(1)).thenReturn(hotelDTO);
        when(categoriaHabitacionService.buscarCategoriaPorId(1)).thenReturn(categoriaHabitacionDTO);
        when(habitacionRepository.save(any(Habitacion.class))).thenReturn(habitacion);

        HabitacionDTO habitacionCreada = habitacionService.crearHabitacion(crearHabitacionDTO);

        verify(hotelService).buscarHotelPorId(1);
        verify(categoriaHabitacionService).buscarCategoriaPorId(1);
        verify(habitacionRepository).save(any(Habitacion.class));
        assertAll(() -> {
            assertEquals(1, habitacionCreada.getId());
            assertEquals(hotelDTO, habitacionCreada.getHotel());
            assertEquals(categoriaHabitacionDTO, habitacionCreada.getCategoriaHabitacion());
        });
    }

    @Test
    void testModificarHabitacion() {
        CrearHabitacionDTO crearHabitacionDTOSecondary = CrearHabitacionDTO.builder()
                .precio(201.0)
                .numero(30)
                .build();
        Habitacion habitacion = habitacionMapper.toEntity(habitacionDTO);
        when(habitacionRepository.findById(1)).thenReturn(Optional.ofNullable(habitacion));
        //when(hotelService.buscarHotelPorId(1)).thenReturn(hotelDTO);
        //when(categoriaHabitacionService.buscarCategoriaPorId(1)).thenReturn(categoriaHabitacionDTO);
        when(habitacionRepository.save(any(Habitacion.class))).thenReturn(habitacion);

        HabitacionDTO habitacionModificada = habitacionService.modificarHabitacion(1, crearHabitacionDTOSecondary);

        verify(habitacionRepository).findById(1);
        //verify(hotelService).buscarHotelPorId(1);
        //verify(categoriaHabitacionService).buscarCategoriaPorId(1);
        verify(habitacionRepository).save(any(Habitacion.class));
        assertAll(() -> {
            assertEquals(201.0, habitacionModificada.getPrecio());
            assertEquals(1, habitacionModificada.getId());
            assertEquals(categoriaHabitacionDTO, habitacionModificada.getCategoriaHabitacion());
            assertEquals(30, habitacionModificada.getNumero());
        });

    }

    @Test
    void testModificarHotelDeHabitacion() {
        CrearHabitacionDTO crearHabitacionDTOSecondary = CrearHabitacionDTO.builder()
                .idHotel(2)
                .build();
        HotelDTO hotelDTOSecondary = HotelDTO.builder()
                .id(2)
                .nombre("ALVEARCITO")
                .direccion(hotelDTO.getDireccion())
                .categoriaHotel(hotelDTO.getCategoriaHotel())
                .build();
        Habitacion habitacion = habitacionMapper.toEntity(habitacionDTO);
        when(habitacionRepository.findById(1)).thenReturn(Optional.ofNullable(habitacion));
        when(hotelService.buscarHotelPorId(2)).thenReturn(hotelDTOSecondary);
        when(habitacionRepository.save(any(Habitacion.class))).thenReturn(habitacion);

        HabitacionDTO habitacionModificada = habitacionService.modificarHabitacion(1, crearHabitacionDTOSecondary);

        verify(habitacionRepository).findById(1);
        verify(hotelService).buscarHotelPorId(2);
        verify(habitacionRepository).save(any(Habitacion.class));
        assertAll(() -> {
            assertEquals(categoriaHabitacionDTO, habitacionModificada.getCategoriaHabitacion());
            assertEquals(hotelDTOSecondary, habitacionModificada.getHotel());
        });

    }

    @Test
    void testModificarCategoriaDeHabitacion() {
        CrearHabitacionDTO crearHabitacionDTOSecondary = CrearHabitacionDTO.builder()
                .precio(201.0)
                .idCategoriaHabitacion(2)
                .build();
        CategoriaHabitacionDTO categoriaHabitacionDTOSecondary = CategoriaHabitacionDTO.builder()
                .id(2)
                .nombre("glaaaa")
                .descripcion("eer erer er")
                .build();
        Habitacion habitacion = habitacionMapper.toEntity(habitacionDTO);
        when(habitacionRepository.findById(1)).thenReturn(Optional.ofNullable(habitacion));
        when(categoriaHabitacionService.buscarCategoriaPorId(2)).thenReturn(categoriaHabitacionDTOSecondary);
        when(habitacionRepository.save(any(Habitacion.class))).thenReturn(habitacion);

        HabitacionDTO habitacionModificada = habitacionService.modificarHabitacion(1, crearHabitacionDTOSecondary);

        verify(habitacionRepository).findById(1);
        verify(categoriaHabitacionService).buscarCategoriaPorId(2);
        verify(habitacionRepository).save(any(Habitacion.class));
        assertAll(() -> {
            assertEquals(201.0, habitacionModificada.getPrecio());
            assertEquals(categoriaHabitacionDTOSecondary, habitacionModificada.getCategoriaHabitacion());
        });

    }
    */
}
