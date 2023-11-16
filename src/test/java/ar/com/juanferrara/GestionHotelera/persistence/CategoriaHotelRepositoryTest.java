package ar.com.juanferrara.GestionHotelera.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.com.juanferrara.GestionHotelera.domain.entity.CategoriaHotel;
import ar.com.juanferrara.GestionHotelera.domain.entity.Direccion;
import ar.com.juanferrara.GestionHotelera.domain.entity.Hotel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CategoriaHotelRepositoryTest {

    @Autowired
    private CategoriaHotelRepository categoriaHotelRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void testSave() {
        CategoriaHotel categoriaHotel = CategoriaHotel.builder()
                .nombre("Explendido")
                .estrellas(5)
                .descripcion("hotel de lujo")
                .build();

        CategoriaHotel categoriaHotelGuardado = categoriaHotelRepository.save(categoriaHotel);

        assertThat(categoriaHotelGuardado).isNotNull();
        assertThat(categoriaHotelGuardado.getId()).isGreaterThan(0);
    }

    @Test
    void testExistenHotelesConCategoria() {
        CategoriaHotel categoriaHotel = CategoriaHotel.builder()
                .nombre("Explendido")
                .estrellas(5)
                .descripcion("hotel de lujo")
                .build();
        CategoriaHotel categoriaHotelGuardado = categoriaHotelRepository.save(categoriaHotel);
        Hotel hotel = Hotel.builder()
                .direccion(Direccion.builder()
                        .direccion("asd")
                        .localidad("asd")
                        .provincia("asd")
                        .build())
                .categoriaHotel(categoriaHotelGuardado)
                .nombre("gran faena")
                .build();
        hotelRepository.save(hotel);

        boolean check = categoriaHotelRepository.checkSiExistenHotelesConCategoria(1);

        assertEquals(true, check);
    }
}
