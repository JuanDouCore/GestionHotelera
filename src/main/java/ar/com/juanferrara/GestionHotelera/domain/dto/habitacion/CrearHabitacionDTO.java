package ar.com.juanferrara.GestionHotelera.domain.dto.habitacion;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrearHabitacionDTO {

    @Positive
    private int idCategoriaHabitacion;

    @Positive
    private double precio;

    @Positive
    private int camas;

    @Positive
    private int capacidad;

}
