package ar.com.juanferrara.GestionHotelera.domain.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HotelDTO {

    private int id;

    @Size(max = 45)
    private String nombre;

    private DireccionDTO direccion;

    private CategoriaHotelDTO categoriaHotel;
}
