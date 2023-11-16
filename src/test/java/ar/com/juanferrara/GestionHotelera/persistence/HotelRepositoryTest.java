package ar.com.juanferrara.GestionHotelera.persistence;

import ar.com.juanferrara.GestionHotelera.domain.entity.CategoriaHotel;
import ar.com.juanferrara.GestionHotelera.domain.entity.Direccion;
import ar.com.juanferrara.GestionHotelera.domain.entity.Hotel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    private Hotel hotel;

    @BeforeEach
    void setup() {
        Direccion direccion = Direccion.builder()
                .direccion("Faena 213")
                .localidad("Ciudad de buenos aires")
                .provincia("Buenos Aires")
                .build();

        hotel = Hotel.builder()
                .nombre("Gran Faena")
                .direccion(direccion)
                .categoriaHotel(CategoriaHotel.builder()
                        .nombre("de lujo")
                        .estrellas(3)
                        .descripcion("hoteles de gran lujo")
                        .build())
                .build();


        hotelRepository.save(hotel);
    }

    @Test
    void testSave() {
        Direccion direccion = Direccion.builder()
                .direccion("Faena 213")
                .localidad("Ciudad de buenos aires")
                .provincia("Buenos Aires")
                .build();

        Hotel hotel = Hotel.builder()
                .nombre("Gran Faena")
                .direccion(direccion)
                .categoriaHotel(CategoriaHotel.builder()
                        .nombre("de lujo")
                        .estrellas(3)
                        .descripcion("hoteles de gran lujo")
                        .build())
                .build();

        Hotel hotelSaved = hotelRepository.save(hotel);

        assertThat(hotelSaved).isNotNull();
        assertThat(hotelSaved.getId()).isGreaterThan(0);
    }

    @Test
    void findById() {
        Direccion direccion = Direccion.builder()
                .direccion("Faena 213")
                .localidad("Ciudad de buenos aires")
                .provincia("Buenos Aires")
                .build();

        Hotel hotel = Hotel.builder()
                .nombre("Gran Faena")
                .direccion(direccion)
                .categoriaHotel(CategoriaHotel.builder()
                        .nombre("de lujo")
                        .estrellas(3)
                        .descripcion("hoteles de gran lujo")
                        .build())
                .build();

        Hotel hotelEncontrado = hotelRepository.findById(0).orElse(null);

        assertThat(hotel).isNotNull();
        assertThat(hotel.getDireccion()).isEqualTo(direccion);
    }

    @Test
    void delete() {
        Hotel hotelSaved = hotelRepository.save(hotel);
        hotelRepository.deleteById(1);

        Direccion direccion = direccionRepository.findById(1).orElse(null);
        
        assertThat(direccion).isNull();
    }

    @Test
    void update() {
        Direccion second = Direccion.builder()
                .direccion("aa")
                .localidad("bb")
                .provincia("cc")
                .build();
        hotel.setDireccion(second);
        Hotel hotelSaved = hotelRepository.save(hotel);
        Direccion direccion = Direccion.builder()
                .direccion("gran danes 321")
                .build();
        hotelSaved.setNombre("UN GRAN HOTEL 2");
        hotelSaved.setDireccion(direccion);

        Hotel holteUpdated = hotelRepository.save(hotelSaved);

        assertThat(holteUpdated).isNotNull();
        assertThat(holteUpdated.getDireccion().getId()).isEqualTo(2);
        assertThat(holteUpdated.getDireccion().getDireccion()).isEqualTo("gran danes 321");
        assertThat(holteUpdated.getNombre()).isEqualTo("UN GRAN HOTEL 2");
    }
}