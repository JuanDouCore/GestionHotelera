package ar.com.juanferrara.GestionHotelera.business.service.impl;

import ar.com.juanferrara.GestionHotelera.business.mapper.impl.CrearHotelMapper;
import ar.com.juanferrara.GestionHotelera.business.mapper.impl.CrearHotelMapperImpl;
import ar.com.juanferrara.GestionHotelera.business.mapper.impl.HotelMapper;
import ar.com.juanferrara.GestionHotelera.business.mapper.impl.HotelMapperImpl;
import ar.com.juanferrara.GestionHotelera.business.service.CategoriaHotelService;
import ar.com.juanferrara.GestionHotelera.domain.dto.categorias.CategoriaHotelDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.hotel.CrearHotelDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.generic.DireccionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.hotel.HotelDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.Hotel;
import ar.com.juanferrara.GestionHotelera.persistence.HotelRepository;
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
public class HotelServiceImplTest {

    @InjectMocks
    private HotelServiceImpl hotelService;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private CategoriaHotelService categoriaHotelService;

    @Spy
    private HotelMapper hotelMapper = new HotelMapperImpl();

    @Spy
    private CrearHotelMapper crearHotelMapper = new CrearHotelMapperImpl();


    private HotelDTO hotelDTO;
    private CrearHotelDTO crearHotelDTO;
    private DireccionDTO direccionDTO;
    private CategoriaHotelDTO categoriaHotelDTO;


    @BeforeEach
    void setup() {
        direccionDTO = DireccionDTO.builder()
                .direccion("SUNCHALES 5703")
                .localidad("gonzalez catan")
                .provincia("buenos aires")
                .build();

        categoriaHotelDTO = CategoriaHotelDTO.builder()
                .id(1)
                .nombre("TURISTA")
                .estrellas(2)
                .descripcion("hoteles para turistas")
                .build();

        crearHotelDTO = CrearHotelDTO.builder()
                .nombre("GRAN FAENA")
                .direccion(direccionDTO)
                .id_categoria(1)
                .build();

        hotelDTO = HotelDTO.builder()
                .id(1)
                .nombre("GRAN FAENA")
                .direccion(direccionDTO)
                .categoriaHotel(categoriaHotelDTO)
                .build();
    }


    @Test
    void testCrearHotel() {
        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        when(categoriaHotelService.buscarCategoriaPorId(1)).thenReturn(categoriaHotelDTO);
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        HotelDTO hotelDTOCreado = hotelService.crearHotel(crearHotelDTO);

        verify(categoriaHotelService).buscarCategoriaPorId(1);
        verify(hotelRepository).save(any(Hotel.class));
        assertAll(() -> {
           assertEquals(1, hotelDTOCreado.getId());
           assertEquals(direccionDTO, hotelDTOCreado.getDireccion());
           assertEquals(categoriaHotelDTO, hotelDTOCreado.getCategoriaHotel());
        });
    }

    @Test
    void buscarHotelPorId() {
        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        when(hotelRepository.findById(1)).thenReturn(Optional.ofNullable(hotel));

        HotelDTO hotelEncontrado = hotelService.buscarHotelPorId(1);

        verify(hotelRepository).findById(1);
        assertAll(() -> {
            assertEquals(1, hotelEncontrado.getId());
            assertEquals("GRAN FAENA", hotelEncontrado.getNombre());
            assertEquals(direccionDTO, hotelEncontrado.getDireccion());
            assertEquals(categoriaHotelDTO, hotelEncontrado.getCategoriaHotel());
        });
    }

    @Test
    void modificarHotel() {
        CategoriaHotelDTO categoriaHotelDTOSecondary = CategoriaHotelDTO.builder()
                .id(2)
                .estrellas(2)
                .nombre("glaaa")
                .descripcion("asdasdasda")
                .build();
        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        when(hotelRepository.findById(1)).thenReturn(Optional.ofNullable(hotel));
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);
        when(categoriaHotelService.buscarCategoriaPorId(2)).thenReturn(categoriaHotelDTOSecondary);
        CrearHotelDTO crearHotelDTOModificado = CrearHotelDTO.builder()
                .id_categoria(2)
                .build();

        HotelDTO hotelModificado = hotelService.modificarHotel(1, crearHotelDTOModificado);

        verify(hotelRepository).findById(1);
        verify(hotelRepository).save(any(Hotel.class));
        verify(categoriaHotelService).buscarCategoriaPorId(2);
        assertAll(() -> {
            assertEquals(1, hotelModificado.getId());
            assertEquals("GRAN FAENA", hotelModificado.getNombre());
            assertEquals(categoriaHotelDTOSecondary, hotelModificado.getCategoriaHotel());
        });
    }
}
