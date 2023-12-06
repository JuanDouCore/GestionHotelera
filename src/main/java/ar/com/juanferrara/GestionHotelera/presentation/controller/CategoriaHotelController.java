package ar.com.juanferrara.GestionHotelera.presentation.controller;

import ar.com.juanferrara.GestionHotelera.business.service.CategoriaHotelService;
import ar.com.juanferrara.GestionHotelera.domain.dto.categorias.CategoriaHotelDTO;
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

@Tag(name = "3. Categoria Hotel", description = "API para la gestión de categorias de hoteles")

@RestController
@RequestMapping("/api/categoriashoteles")

@PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_GERENTE')")
@SecurityRequirement(name = "Bearer Authentication")

@Validated
public class CategoriaHotelController {

    @Autowired
    private CategoriaHotelService categoriaHotelService;

    @Operation(summary = "Crear una categoria de hotel", description = "Crea una categoria de hotel")
    @ApiResponse(responseCode = "201", description = "Categoria de hotel creada")

    @PostMapping()
    public ResponseEntity<CategoriaHotelDTO> crear(@Valid @RequestBody CategoriaHotelDTO categoriaHotelDTO) {
        CategoriaHotelDTO createdCategoriaHotel = categoriaHotelService.crearCategoria(categoriaHotelDTO);
        return ResponseEntity.created(URI.create("/api/categoriashoteles/" + createdCategoriaHotel.getId())).body(createdCategoriaHotel);
    }

    @Operation(summary = "Listar todas las categorias de hoteles", description = "Lista todas las categorias de hoteles")
    @ApiResponse(responseCode = "200", description = "Categorias de hoteles listadas")

    @GetMapping
    public ResponseEntity<List<CategoriaHotelDTO>> listAllCategorias() {
        return ResponseEntity.ok(categoriaHotelService.listarTodasCategorias());
    }

    @Operation(summary = "Buscar una categoria de hotel por id", description = "Busca una categoria de hotel por id")
    @ApiResponse(responseCode = "200", description = "Categoria de hotel encontrada")

    @GetMapping("{id}")
    public ResponseEntity<CategoriaHotelDTO> getCategoria(@PathVariable int id) {
        return ResponseEntity.ok(categoriaHotelService.buscarCategoriaPorId(id));
    }

    @Operation(summary = "Eliminar una categoria de hotel por id", description = "Elimina una categoria de hotel por id")
    @ApiResponse(responseCode = "200", description = "Categoria de hotel eliminada")

    @DeleteMapping("{id}")
    public ResponseEntity<CategoriaHotelDTO> eliminar(@PathVariable int id) {
        return ResponseEntity.ok(categoriaHotelService.eliminarCategoria(id));
    }

    @Operation(summary = "Modificar una categoria de hotel por id", description = "Modifica una categoria de hotel por id")
    @ApiResponse(responseCode = "200", description = "Categoria de hotel modificada")

    @PatchMapping("{id}")
    public ResponseEntity<CategoriaHotelDTO> modificar(@PathVariable int id, @Valid @RequestBody CategoriaHotelDTO categoriaHotelDTO) {
        categoriaHotelDTO.setId(id);
        CategoriaHotelDTO modified = categoriaHotelService.modificarCategoria(categoriaHotelDTO);
        return ResponseEntity.ok(modified);
    }



}
