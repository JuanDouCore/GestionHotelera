package ar.com.juanferrara.GestionHotelera.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class CrearHotelDTO {

    @NotBlank
    private String nombre;

    @NotNull
    private DireccionDTO direccion;

    @Positive
    private int id_categoria;
}
