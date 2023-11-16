package ar.com.juanferrara.GestionHotelera.domain.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CategoriaHabitacionDTO {

    private int id;

    @Size(max = 45)
    private String nombre;

    @Size(max = 255)
    private String descripcion;
}
