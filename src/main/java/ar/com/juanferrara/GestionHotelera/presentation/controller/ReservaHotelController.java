package ar.com.juanferrara.GestionHotelera.presentation.controller;

import ar.com.juanferrara.GestionHotelera.business.service.ReservaService;
import ar.com.juanferrara.GestionHotelera.domain.dto.habitacion.HabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.reservas.CrearReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.reservas.CriterioReservaHabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.reservas.InfoReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.ReservaException;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Tag(name = "Reserva Hotel", description = "API para la gestión de reservas de hoteles")

@RestController
@RequestMapping("/api/hoteles/{idHotel}/reservas")
@Validated
@SecurityRequirement(name = "Bearer Authentication")
public class ReservaHotelController {

    @Autowired
    private ReservaService reservaService;


    @Operation(summary = "Crear una reserva", description = "Crea una reserva")
    @ApiResponse(responseCode = "201", description = "Reserva creada")

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_GERENTE') || hasRole('ROLE_EMPLEADO') && principal.idHotelAsignado == #idHotel")
    @PostMapping
    public ResponseEntity<InfoReservaDTO> crearReserva(@PathVariable int idHotel, @Valid @RequestBody CrearReservaDTO crearReservaDTO) {
        InfoReservaDTO reservaCreada = reservaService.crearReserva(idHotel, crearReservaDTO);
        return ResponseEntity.created(URI.create("/api/hoteles/" + idHotel + "/reservas/" + reservaCreada.getId())).body(reservaCreada);
    }

    @Operation(summary = "Confirmar una reserva", description = "Confirma el comienzo de estadia de una reserva")
    @ApiResponse(responseCode = "200", description = "Reserva confirmada")

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_GERENTE') || hasRole('ROLE_EMPLEADO') && principal.idHotelAsignado == #idHotel")
    @PatchMapping("{id}/comienzoestadia")
    public ResponseEntity<InfoReservaDTO> confirmarComienzoEstadia(@PathVariable int idHotel, @PathVariable int id, @RequestParam double cantidadAbonada) {
        return ResponseEntity.ok(reservaService.confirmarComienzoEstadia(idHotel, id, cantidadAbonada));
    }

    @Operation(summary = "Listar reservas de cliente", description = "Lista las reservas de un cliente")
    @ApiResponse(responseCode = "200", description = "Reservas listadas")

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_GERENTE') || hasRole('ROLE_EMPLEADO') && principal.idHotelAsignado == #idHotel")
    @GetMapping
    public ResponseEntity<List<InfoReservaDTO>> obtenerReservasDeCliente(@PathVariable int idHotel, @RequestParam Integer dniCliente) {
        return ResponseEntity.ok(reservaService.obtenerReservasDeUnCliente(dniCliente));
    }

    @Operation(summary = "Buscar primera habitacion disponible", description = "Busca la primera habitacion disponible segun el criterio de reserva")
    @ApiResponse(responseCode = "200", description = "Habitacion encontrada")

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_GERENTE') || hasRole('ROLE_EMPLEADO') && principal.idHotelAsignado == #idHotel")
    @GetMapping("disponible")
    public ResponseEntity<HabitacionDTO> obtenerHabitacionDisponibleSegunCriterio(@PathVariable int idHotel, @Valid @RequestBody CriterioReservaHabitacionDTO criterioReservaHabitacionDTO) {
        return ResponseEntity.ok(reservaService.obtenerHabitacionDisponibleSegunCriterio(idHotel, criterioReservaHabitacionDTO));
    }

    @Operation(summary = "Buscar habitaciones disponibles en rango de fecha", description = "Busca las habitaciones disponibles en un rango de fecha segun el criterio de reserva")
    @ApiResponse(responseCode = "200", description = "Habitaciones encontradas")

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_GERENTE') || hasRole('ROLE_EMPLEADO') && principal.idHotelAsignado == #idHotel")
    @GetMapping("disponibles")
    public ResponseEntity<Map<Date, List<Integer>>> obtenerHabitacionesDisponiblesEnRangoDeFecha(@PathVariable int idHotel, @Valid @RequestBody CriterioReservaHabitacionDTO criterioReservaHabitacionDTO, @RequestParam String fechaMesYAnio) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;

        try {
            fecha = dateFormat.parse(String.valueOf(fechaMesYAnio));
        } catch (ParseException e) {
            throw new ReservaException("Fecha introducida invalida");
        }

        return ResponseEntity.ok(reservaService.obtenerHabitacionesDisponiblesEnRangoDeFecha(idHotel, criterioReservaHabitacionDTO, fecha));
    }
}
