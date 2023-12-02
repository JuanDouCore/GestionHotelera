package ar.com.juanferrara.GestionHotelera.business.service.impl;

import ar.com.juanferrara.GestionHotelera.business.mapper.impl.CategoriaHotelMapper;
import ar.com.juanferrara.GestionHotelera.business.mapper.impl.CategoriaHotelMapperImpl;
import ar.com.juanferrara.GestionHotelera.domain.dto.categorias.CategoriaHotelDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.CategoriaHotel;
import ar.com.juanferrara.GestionHotelera.persistence.CategoriaHotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoriaHotelServiceImplTest {

    @InjectMocks
    private CategoriaHotelServiceImpl categoriaHotelService;

    @Mock
    private CategoriaHotelRepository categoriaHotelRepository;

    @Spy
    private CategoriaHotelMapper categoriaHotelMapper = new CategoriaHotelMapperImpl();


    @Test
    void testCrearCategoria() {
        CategoriaHotelDTO categoriaHotelDTO = CategoriaHotelDTO.builder()
                .id(1)
                .nombre("lujo")
                .estrellas(5)
                .descripcion("hoteles de lujo")
                .build();
        CategoriaHotel categoriaHotel = categoriaHotelMapper.toEntity(categoriaHotelDTO);
        Mockito.when(categoriaHotelRepository.save(Mockito.any(CategoriaHotel.class))).thenReturn(categoriaHotel);

        CategoriaHotelDTO categoriaHotelDTOSaved = categoriaHotelService.crearCategoria(categoriaHotelDTO);

        verify(categoriaHotelRepository).save(Mockito.any(CategoriaHotel.class));
        assertAll(() -> {
            assertEquals(1, categoriaHotelDTOSaved.getId());
            assertEquals("lujo", categoriaHotelDTOSaved.getNombre());
            assertEquals(5, categoriaHotelDTOSaved.getEstrellas());
            assertEquals("hoteles de lujo", categoriaHotelDTOSaved.getDescripcion());
        });
    }

    @Test
    void buscarCategoriaPorId() {
        CategoriaHotel categoriaHotel = CategoriaHotel.builder()
                .id(1)
                .nombre("lujo")
                .estrellas(5)
                .descripcion("hoteles de lujo")
                .build();
        Mockito.when(categoriaHotelRepository.findById(1)).thenReturn(Optional.ofNullable(categoriaHotel));

        CategoriaHotelDTO categoriaHotelDTO = categoriaHotelService.buscarCategoriaPorId(1);

        verify(categoriaHotelRepository).findById(1);
        assertAll(() -> {
            assertEquals("lujo", categoriaHotelDTO.getNombre());
            assertEquals(5, categoriaHotelDTO.getEstrellas());
        });
    }


    @Test
    void modificarCategoria() {
        CategoriaHotel categoriaHotel = CategoriaHotel.builder()
                .id(1)
                .nombre("de lujo")
                .estrellas(5)
                .descripcion("hoteles de lujo")
                .build();
        when(categoriaHotelRepository.findById(1)).thenReturn(Optional.ofNullable(categoriaHotel));
        when(categoriaHotelRepository.save(Mockito.any(CategoriaHotel.class))).thenReturn(categoriaHotel);
        CategoriaHotelDTO categoriaHotelDTO = CategoriaHotelDTO.builder()
                .id(1)
                .estrellas(3)
                .build();

        CategoriaHotelDTO categoriaHotelDTOModified = categoriaHotelService.modificarCategoria(categoriaHotelDTO);

        verify(categoriaHotelRepository).findById(1);
        verify(categoriaHotelRepository).save(Mockito.any(CategoriaHotel.class));
        assertAll(() -> {
            assertEquals("de lujo", categoriaHotelDTOModified.getNombre());
            assertEquals(3, categoriaHotelDTOModified.getEstrellas());
            assertEquals("hoteles de lujo", categoriaHotelDTOModified.getDescripcion());
        });
    }
}
