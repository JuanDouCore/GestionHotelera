package ar.com.juanferrara.GestionHotelera.presentation.controller;

import ar.com.juanferrara.GestionHotelera.business.service.HabitacionService;
import ar.com.juanferrara.GestionHotelera.domain.dto.habitacion.CrearHabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.habitacion.HabitacionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "6. Habitacion", description = "API para la gestión de habitaciones")

@RestController
@RequestMapping("/api/hoteles/{idhotel}/habitaciones")
@SecurityRequirement(name = "Bearer Authentication")
@Validated
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    @Operation(summary = "Crear una habitacion", description = "Crea una habitacion")
    @ApiResponse(responseCode = "201", description = "Habitacion creada")

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_GERENTE')")
    @PostMapping
    public ResponseEntity<HabitacionDTO> crearHabitacion(@PathVariable int idhotel, @Valid @RequestBody CrearHabitacionDTO crearHabitacionDTO) {
        HabitacionDTO habitacionCreada = habitacionService.crearHabitacion(idhotel, crearHabitacionDTO);
        return ResponseEntity.created(URI.create("/api/hoteles/" + idhotel + "/" + habitacionCreada.getNumero())).body(habitacionCreada);
    }

    @Operation(summary = "Listar todas las habitaciones de un hotel", description = "Lista todas las habitaciones de un hotel")
    @ApiResponse(responseCode = "200", description = "Habitaciones listadas")

    @PreAuthorize("hasRole('ROLE_EMPLEADO') || hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_GERENTE')")
    @GetMapping
    public ResponseEntity<List<HabitacionDTO>> listAllHabitacionesDeUnHotel(@PathVariable int idhotel) {
        return ResponseEntity.ok(habitacionService.listarTodasLasHabitacionesDeUnHotel(idhotel));
    }

    @Operation(summary = "Buscar una habitacion por numero y hotel", description = "Busca una habitacion por numero y hotel")
    @ApiResponse(responseCode = "200", description = "Habitacion encontrada")

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_GERENTE') || hasRole('ROLE_EMPELADO') && principal.idHotelAsignado == #idhotel")
    @GetMapping("{numeroHabitacion}")
    public ResponseEntity<HabitacionDTO> getHabitacion(@PathVariable int idhotel, @PathVariable int numeroHabitacion) {
        return ResponseEntity.ok(habitacionService.buscarHabitacionPorNroYHotel(idhotel, numeroHabitacion));
    }

    @Operation(summary = "Eliminar una habitacion por numero y hotel", description = "Elimina una habitacion por numero y hotel")
    @ApiResponse(responseCode = "200", description = "Habitacion eliminada")

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_GERENTE')")
    @DeleteMapping("{numeroHabitacion}")
    public ResponseEntity<HabitacionDTO> eliminarHabitacion(@PathVariable int idhotel, @PathVariable int numeroHabitacion) {
        return ResponseEntity.ok(habitacionService.eliminarHabitacion(idhotel, numeroHabitacion));
    }

    @Operation(summary = "Modificar una habitacion por numero y hotel", description = "Modifica una habitacion por numero y hotel")
    @ApiResponse(responseCode = "200", description = "Habitacion modificada")

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_GERENTE')")
    @PatchMapping("{numeroHabitacion}")
    public ResponseEntity<HabitacionDTO> modificarHabitacion(@PathVariable int idhotel, @PathVariable int numeroHabitacion, @RequestBody CrearHabitacionDTO crearHabitacionDTO) {
        HabitacionDTO habitacionModificada = habitacionService.modificarHabitacion(idhotel, numeroHabitacion, crearHabitacionDTO);
        return ResponseEntity.ok(habitacionModificada);
    }
}
