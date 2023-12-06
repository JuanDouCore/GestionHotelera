package ar.com.juanferrara.GestionHotelera.presentation.controller;

import ar.com.juanferrara.GestionHotelera.business.service.HotelService;
import ar.com.juanferrara.GestionHotelera.domain.dto.hotel.CrearHotelDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.hotel.HotelDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "4. Hotel", description = "API para la gestión de hoteles")

@Controller
@RequestMapping("/api/hoteles")

@PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_GERENTE')")
@SecurityRequirement(name = "Bearer Authentication")

@Validated
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Operation(summary = "Crear un hotel", description = "Crea un hotel con una categoria asignada", security = {@SecurityRequirement(name = "Admin Authentication")})
    @ApiResponse(responseCode = "201", description = "Hotel creado")

    @PostMapping
    public ResponseEntity<HotelDTO> crearHotel(@Valid @RequestBody CrearHotelDTO crearHotelDTO) {
        HotelDTO hotelCreado = hotelService.crearHotel(crearHotelDTO);
        return ResponseEntity.created(URI.create("/api/hoteles/" + hotelCreado.getId())).body(hotelCreado);
    }

    @Operation(summary = "Listar todos los hoteles", description = "Lista todos los hoteles")
    @ApiResponse(responseCode = "200", description = "Hoteles listados")


    @GetMapping
    public ResponseEntity<List<HotelDTO>> listAllHoteles() {
        return ResponseEntity.ok(hotelService.listarTodosHoteles());
    }

    @Operation(summary = "Buscar un hotel por id", description = "Busca un hotel por id")
    @ApiResponse(responseCode = "200", description = "Hotel encontrado")

    @GetMapping("{id}")
    public ResponseEntity<HotelDTO> getHotel(@PathVariable int id) {
        return ResponseEntity.ok(hotelService.buscarHotelPorId(id));
    }

    @Operation(summary = "Eliminar un hotel por id", description = "Elimina un hotel por id")
    @ApiResponse(responseCode = "200", description = "Hotel eliminado")

    @DeleteMapping("{id}")
    public ResponseEntity<HotelDTO> eliminarHotel(@PathVariable int id) {
        return ResponseEntity.ok(hotelService.eliminarHotel(id));
    }

    @Operation(summary = "Modificar un hotel por id", description = "Modifica un hotel por id")
    @ApiResponse(responseCode = "200", description = "Hotel modificado")

    @PatchMapping("{id}")
    public ResponseEntity<HotelDTO> modificarHotel(@PathVariable int id,@RequestBody CrearHotelDTO crearHotelDTO) {
        HotelDTO hotelModificado = hotelService.modificarHotel(id, crearHotelDTO);
        return ResponseEntity.ok(hotelModificado);
    }
}
