package ar.com.juanferrara.GestionHotelera.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CategoriaHotelDTO {

    private int id;

    @Size(max = 45)
    private String nombre;

    @PositiveOrZero
    @Max(5)
    private int estrellas;

    @Size(max = 255)
    private String descripcion;
}
