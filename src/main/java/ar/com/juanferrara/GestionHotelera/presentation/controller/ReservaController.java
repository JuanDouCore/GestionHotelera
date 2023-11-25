package ar.com.juanferrara.GestionHotelera.presentation.controller;

import ar.com.juanferrara.GestionHotelera.business.service.ReservaService;
import ar.com.juanferrara.GestionHotelera.domain.dto.CrearReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.ReservaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping("{id}")
    public ResponseEntity<ReservaDTO> getReserva(@PathVariable int id) {
        return ResponseEntity.ok(reservaService.buscarReservaPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> listAllReservas() {
        return ResponseEntity.ok(reservaService.listarTodasReservas());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ReservaDTO> eliminarReserva(@PathVariable int id) {
        return ResponseEntity.ok(reservaService.eliminarReserva(id));
    }

    @PatchMapping("{id}")
    public ResponseEntity<ReservaDTO> modificarReserva(@PathVariable int id, @RequestBody CrearReservaDTO reservaDTO) {
        ReservaDTO reservaModificada = reservaService.modificarReserva(id, reservaDTO);
        return ResponseEntity.ok(reservaModificada);
    }


}
