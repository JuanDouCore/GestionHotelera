package ar.com.juanferrara.GestionHotelera.presentation.controller;

import ar.com.juanferrara.GestionHotelera.business.service.CategoriaHotelService;
import ar.com.juanferrara.GestionHotelera.domain.dto.CategoriaHotelDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categoriashoteles")
@Validated
public class CategoriaHotelController {

    @Autowired
    private CategoriaHotelService categoriaHotelService;

    @PostMapping()
    public ResponseEntity<CategoriaHotelDTO> crear(@Valid @RequestBody CategoriaHotelDTO categoriaHotelDTO) {
        CategoriaHotelDTO createdCategoriaHotel = categoriaHotelService.crearCategoria(categoriaHotelDTO);
        return ResponseEntity.created(URI.create("/api/categoriashoteles/" + createdCategoriaHotel.getId())).body(createdCategoriaHotel);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaHotelDTO>> listAllCategorias() {
        return ResponseEntity.ok(categoriaHotelService.listarTodasCategorias());
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoriaHotelDTO> getCategoria(@PathVariable int id) {
        return ResponseEntity.ok(categoriaHotelService.buscarCategoriaPorId(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CategoriaHotelDTO> eliminar(@PathVariable int id) {
        return ResponseEntity.ok(categoriaHotelService.eliminarCategoria(id));
    }

    @PatchMapping("{id}")
    public ResponseEntity<CategoriaHotelDTO> modificar(@PathVariable int id, @Valid @RequestBody CategoriaHotelDTO categoriaHotelDTO) {
        categoriaHotelDTO.setId(id);
        CategoriaHotelDTO modified = categoriaHotelService.modificarCategoria(categoriaHotelDTO);
        return ResponseEntity.ok(modified);
    }



}
