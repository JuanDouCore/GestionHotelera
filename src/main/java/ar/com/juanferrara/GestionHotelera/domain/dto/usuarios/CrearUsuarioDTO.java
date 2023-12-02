package ar.com.juanferrara.GestionHotelera.domain.dto.usuarios;

import ar.com.juanferrara.GestionHotelera.domain.enums.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CrearUsuarioDTO {

    @Positive
    private int dni;

    @Pattern(regexp="^\\S+$")
    @NotNull
    private String username;

    @NotBlank
    @NotNull
    private String nombre;

    @NotBlank
    @Size(min = 8)
    private String password;

    @Positive
    private int idHotelAsignado;

}
