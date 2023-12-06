package ar.com.juanferrara.GestionHotelera.presentation.controller;

import ar.com.juanferrara.GestionHotelera.business.service.CategoriaHabitacionService;
import ar.com.juanferrara.GestionHotelera.domain.dto.categorias.CategoriaHabitacionDTO;
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

@Tag(name = "5. Categoria Habitacion", description = "API para la gestión de categorias de habitaciones")

@RestController
@RequestMapping("/api/categoriashabitaciones")

@PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_GERENTE')")
@SecurityRequirement(name = "Bearer Authentication")

@Validated
public class CategoriaHabitacionController {

    @Autowired
    private CategoriaHabitacionService categoriaHabitacionService;

    @Operation(summary = "Crear una categoria de habitacion", description = "Crea una categoria de habitacion")
    @ApiResponse(responseCode = "201", description = "Categoria de habitacion creada")

    @PostMapping()
    public ResponseEntity<CategoriaHabitacionDTO> crear(@Valid @RequestBody CategoriaHabitacionDTO categoriaHabitacionDTO) {
        CategoriaHabitacionDTO createdCategoriaHabitacion = categoriaHabitacionService.crearCategoria(categoriaHabitacionDTO);
        return ResponseEntity.created(URI.create("/api/categoriashoteles/" + createdCategoriaHabitacion.getId())).body(createdCategoriaHabitacion);
    }

    @Operation(summary = "Listar todas las categorias de habitaciones", description = "Lista todas las categorias de habitaciones")
    @ApiResponse(responseCode = "200", description = "Categorias de habitaciones listadas")

    @GetMapping
    public ResponseEntity<List<CategoriaHabitacionDTO>> listAllCategorias() {
        return ResponseEntity.ok(categoriaHabitacionService.listarTodasCategorias());
    }

    @Operation(summary = "Buscar una categoria de habitacion por id", description = "Busca una categoria de habitacion por id")
    @ApiResponse(responseCode = "200", description = "Categoria de habitacion encontrada")

    @GetMapping("{id}")
    public ResponseEntity<CategoriaHabitacionDTO> getCategoria(@PathVariable int id) {
        return ResponseEntity.ok(categoriaHabitacionService.buscarCategoriaPorId(id));
    }

    @Operation(summary = "Eliminar una categoria de habitacion por id", description = "Elimina una categoria de habitacion por id")
    @ApiResponse(responseCode = "200", description = "Categoria de habitacion eliminada")

    @DeleteMapping("{id}")
    public ResponseEntity<CategoriaHabitacionDTO> eliminar(@PathVariable int id) {
        return ResponseEntity.ok(categoriaHabitacionService.eliminarCategoria(id));
    }

    @Operation(summary = "Modificar una categoria de habitacion por id", description = "Modifica una categoria de habitacion por id")
    @ApiResponse(responseCode = "200", description = "Categoria de habitacion modificada")

    @PatchMapping("{id}")
    public ResponseEntity<CategoriaHabitacionDTO> modificar(@PathVariable int id, @RequestBody CategoriaHabitacionDTO categoriaHabitacionDTO) {
        categoriaHabitacionDTO.setId(id);
        CategoriaHabitacionDTO modified = categoriaHabitacionService.modificarCategoria(categoriaHabitacionDTO);
        return ResponseEntity.ok(modified);
    }
}
