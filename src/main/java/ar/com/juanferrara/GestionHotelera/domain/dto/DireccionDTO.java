package ar.com.juanferrara.GestionHotelera.domain.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DireccionDTO {

    @Size(max = 45)
    private String direccion;

    @Size(max = 45)
    private String localidad;

    @Size(max = 45)
    private String provincia;
}
