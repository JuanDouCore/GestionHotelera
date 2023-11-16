package ar.com.juanferrara.GestionHotelera.presentation.controller;

import ar.com.juanferrara.GestionHotelera.business.service.CategoriaHabitacionService;
import ar.com.juanferrara.GestionHotelera.domain.dto.CategoriaHabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.CategoriaHotelDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.CategoriaHabitacion;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categoriashabitaciones")
@Validated
public class CategoriaHabitacionController {

    @Autowired
    private CategoriaHabitacionService categoriaHabitacionService;

    @PostMapping()
    public ResponseEntity<CategoriaHabitacionDTO> crear(@Valid @RequestBody CategoriaHabitacionDTO categoriaHabitacionDTO) {
        CategoriaHabitacionDTO createdCategoriaHabitacion = categoriaHabitacionService.crearCategoria(categoriaHabitacionDTO);
        return ResponseEntity.created(URI.create("/api/categoriashoteles/" + createdCategoriaHabitacion.getId())).body(createdCategoriaHabitacion);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaHabitacionDTO>> listAllCategorias() {
        return ResponseEntity.ok(categoriaHabitacionService.listarTodasCategorias());
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoriaHabitacionDTO> getCategoria(@PathVariable int id) {
        return ResponseEntity.ok(categoriaHabitacionService.buscarCategoriaPorId(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CategoriaHabitacionDTO> eliminar(@PathVariable int id) {
        return ResponseEntity.ok(categoriaHabitacionService.eliminarCategoria(id));
    }

    @PatchMapping("{id}")
    public ResponseEntity<CategoriaHabitacionDTO> modificar(@PathVariable int id, @RequestBody CategoriaHabitacionDTO categoriaHabitacionDTO) {
        categoriaHabitacionDTO.setId(id);
        CategoriaHabitacionDTO modified = categoriaHabitacionService.modificarCategoria(categoriaHabitacionDTO);
        return ResponseEntity.ok(modified);
    }
}
