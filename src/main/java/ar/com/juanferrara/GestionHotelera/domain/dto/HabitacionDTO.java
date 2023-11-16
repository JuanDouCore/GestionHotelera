package ar.com.juanferrara.GestionHotelera.domain.dto;

import ar.com.juanferrara.GestionHotelera.domain.entity.Hotel;
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
