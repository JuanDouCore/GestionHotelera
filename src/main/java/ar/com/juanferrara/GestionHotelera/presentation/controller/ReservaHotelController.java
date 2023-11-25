package ar.com.juanferrara.GestionHotelera.presentation.controller;

import ar.com.juanferrara.GestionHotelera.business.service.ReservaService;
import ar.com.juanferrara.GestionHotelera.domain.dto.CrearReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.CriterioReservaHabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.HabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.ReservaDTO;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.ReservaException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hoteles/{idHotel}/reservas")
@Validated
public class ReservaHotelController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaDTO> crearReserva(@PathVariable int idHotel, @Valid @RequestBody CrearReservaDTO crearReservaDTO) {
        ReservaDTO reservaCreada = reservaService.crearReserva(idHotel, crearReservaDTO);
        return ResponseEntity.created(URI.create("/api/hoteles/" + idHotel + "/reservas/" + reservaCreada.getId())).body(reservaCreada);
    }

    @PatchMapping("{id}")
    public ResponseEntity<ReservaDTO> confirmarComienzoEstadia(@PathVariable int idHotel, @PathVariable int id, @RequestParam double cantidadAbonada) {
        return ResponseEntity.ok(reservaService.confirmarComienzoEstadia(idHotel, id, cantidadAbonada));
    }

    @GetMapping("disponible")
    public ResponseEntity<HabitacionDTO> obtenerHabitacionDisponibleSegunCriterio(@PathVariable int idHotel, @Valid @RequestBody CriterioReservaHabitacionDTO criterioReservaHabitacionDTO) {
        return ResponseEntity.ok(reservaService.obtenerHabitacionDisponibleSegunCriterio(idHotel, criterioReservaHabitacionDTO));
    }

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
