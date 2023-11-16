package ar.com.juanferrara.GestionHotelera.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.juanferrara.GestionHotelera.domain.entity.CategoriaHabitacion;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CategoriaHabitacionRepositoryTest {

    @Autowired
    private CategoriaHabitacionRepository categoriaHabitacionRepository;

    @Test
    void testSave() {
        CategoriaHabitacion categoriaHabitacion = CategoriaHabitacion.builder()
                .nombre("lujo")
                .descripcion("una gran categoria")
                .build();

        CategoriaHabitacion categoriaHabitacionGuardada = categoriaHabitacionRepository.save(categoriaHabitacion);

        assertThat(categoriaHabitacionGuardada).isNotNull();
        assertThat(categoriaHabitacionGuardada.getId()).isGreaterThan(0);
    }
}
