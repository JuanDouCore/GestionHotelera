package ar.com.juanferrara.GestionHotelera.domain.dto.usuarios;

import ar.com.juanferrara.GestionHotelera.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UsuarioDTO {

    private int dni;
    private String username;
    private String nombre;
    private Role role;
    private int idHotelAsignado;
    private String nombreHotelAsignado;
}
