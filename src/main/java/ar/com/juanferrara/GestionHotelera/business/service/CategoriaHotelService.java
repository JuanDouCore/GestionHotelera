package ar.com.juanferrara.GestionHotelera.business.service;

import ar.com.juanferrara.GestionHotelera.domain.dto.categorias.CategoriaHotelDTO;

import java.util.List;

public interface CategoriaHotelService {

    CategoriaHotelDTO crearCategoria(CategoriaHotelDTO categoriaHotelDTO);
    CategoriaHotelDTO buscarCategoriaPorId(int id);
    CategoriaHotelDTO eliminarCategoria(int id);
    CategoriaHotelDTO modificarCategoria(CategoriaHotelDTO categoriaHotelDTO);
    boolean existeCategoriaPorId(int id);
    List<CategoriaHotelDTO> listarTodasCategorias();


}
