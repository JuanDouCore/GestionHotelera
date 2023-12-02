package ar.com.juanferrara.GestionHotelera.business.mapper.impl;

import ar.com.juanferrara.GestionHotelera.domain.dto.categorias.CategoriaHabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.CategoriaHabitacion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoriaHabitacionMapperTest {

    @Autowired
    private CategoriaHabitacionMapper categoriaHabitacionMapper;

    @Test
    void dtoToEntity() {
        CategoriaHabitacionDTO categoriaHabitacionDTO = CategoriaHabitacionDTO.builder()
                .id(10)
                .nombre("lujo")
                .descripcion("espectacular")
                .build();

        CategoriaHabitacion categoriaHabitacion = categoriaHabitacionMapper.toEntity(categoriaHabitacionDTO);

        assertThat(categoriaHabitacion).isNotNull();
        assertThat(categoriaHabitacion.getDescripcion()).isEqualTo(categoriaHabitacionDTO.getDescripcion());
    }
}
