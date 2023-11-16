package ar.com.juanferrara.GestionHotelera.presentation.controller;

import ar.com.juanferrara.GestionHotelera.business.service.HotelService;
import ar.com.juanferrara.GestionHotelera.domain.dto.CrearHotelDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.HotelDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api/hoteles")
@Validated
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDTO> crearHotel(@Valid @RequestBody CrearHotelDTO crearHotelDTO) {
        HotelDTO hotelCreado = hotelService.crearHotel(crearHotelDTO);
        return ResponseEntity.created(URI.create("/api/hoteles/" + hotelCreado.getId())).body(hotelCreado);
    }

    @GetMapping
    public ResponseEntity<List<HotelDTO>> listAllHoteles() {
        return ResponseEntity.ok(hotelService.listarTodosHoteles());
    }

    @GetMapping("{id}")
    public ResponseEntity<HotelDTO> getHotel(@PathVariable int id) {
        return ResponseEntity.ok(hotelService.buscarHotelPorId(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HotelDTO> eliminarHotel(@PathVariable int id) {
        return ResponseEntity.ok(hotelService.eliminarHotel(id));
    }

    @PatchMapping("{id}")
    public ResponseEntity<HotelDTO> modificarHotel(@PathVariable int id,@RequestBody CrearHotelDTO crearHotelDTO) {
        HotelDTO hotelModificado = hotelService.modificarHotel(id, crearHotelDTO);
        return ResponseEntity.ok(hotelModificado);
    }
}
