package ar.com.juanferrara.GestionHotelera.presentation.controller;

import ar.com.juanferrara.GestionHotelera.business.service.ReservaService;
import ar.com.juanferrara.GestionHotelera.domain.dto.reservas.CrearReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.reservas.ReservaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reserva", description = "API para la gestión de reservas")

@RestController
@RequestMapping("/api/reservas")
@SecurityRequirement(name = "Bearer Authentication")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Operation(summary = "Buscar una reserva", description = "Busca una reserva por id")
    @ApiResponse(responseCode = "200", description = "Reserva encontrada")

    @PreAuthorize("hasRole('ROLE_EMPELADO') || hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_GERENTE')")
    @GetMapping("{id}")
    public ResponseEntity<ReservaDTO> getReserva(@PathVariable int id) {
        return ResponseEntity.ok(reservaService.buscarReservaPorId(id));
    }

    @Operation(summary = "Listar todas las reservas", description = "Lista todas las reservas")
    @ApiResponse(responseCode = "200", description = "Reservas listadas")

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_GERENTE')")
    @GetMapping
    public ResponseEntity<List<ReservaDTO>> listAllReservas() {
        return ResponseEntity.ok(reservaService.listarTodasReservas());
    }

    @Operation(summary = "Eliminar una reserva", description = "Elimina una reserva por id")
    @ApiResponse(responseCode = "200", description = "Reserva eliminada")

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_GERENTE')")
    @DeleteMapping("{id}")
    public ResponseEntity<ReservaDTO> eliminarReserva(@PathVariable int id) {
        return ResponseEntity.ok(reservaService.eliminarReserva(id));
    }

    @Operation(summary = "Modificar reserva", description = "Modifica una reserva por id")
    @ApiResponse(responseCode = "200", description = "Reserva modificada")

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_GERENTE')")
    @PatchMapping("{id}")
    public ResponseEntity<ReservaDTO> modificarReserva(@PathVariable int id, @RequestBody CrearReservaDTO reservaDTO) {
        ReservaDTO reservaModificada = reservaService.modificarReserva(id, reservaDTO);
        return ResponseEntity.ok(reservaModificada);
    }


}
