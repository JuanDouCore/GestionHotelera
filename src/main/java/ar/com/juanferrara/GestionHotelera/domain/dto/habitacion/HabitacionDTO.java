package ar.com.juanferrara.GestionHotelera.domain.dto.habitacion;

import ar.com.juanferrara.GestionHotelera.domain.dto.hotel.HotelDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.categorias.CategoriaHabitacionDTO;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HabitacionDTO {

    private HotelDTO hotel;

    private CategoriaHabitacionDTO categoriaHabitacion;

    @Positive
    private int numero;

    @Positive
    private double precio;

    @Positive
    private int camas;

    @Positive
    private int capacidad;
}
