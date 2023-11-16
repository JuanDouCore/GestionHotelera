package ar.com.juanferrara.GestionHotelera.presentation.controller;

import ar.com.juanferrara.GestionHotelera.business.service.HabitacionService;
import ar.com.juanferrara.GestionHotelera.domain.dto.CrearHabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.HabitacionDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/hoteles/{idhotel}/habitaciones")
@Validated
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    @PostMapping
    public ResponseEntity<HabitacionDTO> crearHabitacion(@PathVariable int idhotel, @Valid @RequestBody CrearHabitacionDTO crearHabitacionDTO) {
        HabitacionDTO habitacionCreada = habitacionService.crearHabitacion(idhotel, crearHabitacionDTO);
        return ResponseEntity.created(URI.create("/api/hoteles/" + idhotel + "/" + habitacionCreada.getNumero())).body(habitacionCreada);
    }

    @GetMapping
    public ResponseEntity<List<HabitacionDTO>> listAllHabitacionesDeUnHotel(@PathVariable int idhotel) {
        return ResponseEntity.ok(habitacionService.listarTodasLasHabitacionesDeUnHotel(idhotel));
    }

    @GetMapping("{numeroHabitacion}")
    public ResponseEntity<HabitacionDTO> getHabitacion(@PathVariable int idhotel, @PathVariable int numeroHabitacion) {
        return ResponseEntity.ok(habitacionService.buscarHabitacionPorNroYHotel(idhotel, numeroHabitacion));
    }

    @DeleteMapping("{numeroHabitacion}")
    public ResponseEntity<HabitacionDTO> eliminarHabitacion(@PathVariable int idhotel, @PathVariable int numeroHabitacion) {
        return ResponseEntity.ok(habitacionService.eliminarHabitacion(idhotel, numeroHabitacion));
    }

    @PatchMapping("{numeroHabitacion}")
    public ResponseEntity<HabitacionDTO> modificarHabitacion(@PathVariable int idhotel, @PathVariable int numeroHabitacion, @RequestBody CrearHabitacionDTO crearHabitacionDTO) {
        HabitacionDTO habitacionModificada = habitacionService.modificarHabitacion(idhotel, numeroHabitacion, crearHabitacionDTO);
        return ResponseEntity.ok(habitacionModificada);
    }
}
