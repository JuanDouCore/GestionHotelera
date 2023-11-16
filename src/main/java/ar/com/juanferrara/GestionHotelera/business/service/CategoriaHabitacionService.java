package ar.com.juanferrara.GestionHotelera.business.service;

import ar.com.juanferrara.GestionHotelera.domain.dto.CategoriaHabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.CategoriaHotelDTO;

import java.util.List;

public interface CategoriaHabitacionService {

    CategoriaHabitacionDTO crearCategoria(CategoriaHabitacionDTO categoriaHabitacionDTO);
    CategoriaHabitacionDTO buscarCategoriaPorId(int id);
    CategoriaHabitacionDTO eliminarCategoria(int id);
    CategoriaHabitacionDTO modificarCategoria(CategoriaHabitacionDTO categoriaHabitacionDTO);
    boolean existeCategoriaPorId(int id);
    List<CategoriaHabitacionDTO> listarTodasCategorias();
}
