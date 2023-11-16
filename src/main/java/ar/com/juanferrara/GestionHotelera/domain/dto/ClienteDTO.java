package ar.com.juanferrara.GestionHotelera.domain.dto;

import ar.com.juanferrara.GestionHotelera.domain.entity.Direccion;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ClienteDTO {

    private int dni;

    @Size(max = 45)
    private String nombre;

    @Size(max = 45)
    private String apellido;

    @Positive
    private int telefono;

    @Email
    private String email;

    private DireccionDTO direccion;
}
