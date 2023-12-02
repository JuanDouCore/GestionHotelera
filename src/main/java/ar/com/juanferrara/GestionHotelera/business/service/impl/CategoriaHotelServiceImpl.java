package ar.com.juanferrara.GestionHotelera.business.service.impl;

import ar.com.juanferrara.GestionHotelera.business.mapper.impl.CategoriaHotelMapper;
import ar.com.juanferrara.GestionHotelera.business.service.CategoriaHotelService;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.CategoriaException;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.NotFoundException;
import ar.com.juanferrara.GestionHotelera.domain.dto.categorias.CategoriaHotelDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.CategoriaHotel;
import ar.com.juanferrara.GestionHotelera.persistence.CategoriaHotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaHotelServiceImpl implements CategoriaHotelService {

    @Autowired
    private CategoriaHotelRepository categoriaHotelRepository;

    @Autowired
    private CategoriaHotelMapper categoriaHotelMapper;


    @Override
    public CategoriaHotelDTO crearCategoria(CategoriaHotelDTO categoriaHotelDTO) {
        CategoriaHotel categoriaHotel = categoriaHotelMapper.toEntity(categoriaHotelDTO);

        return categoriaHotelMapper.toDto(categoriaHotelRepository.save(categoriaHotel));
    }

    @Override
    public CategoriaHotelDTO buscarCategoriaPorId(int id) {
        CategoriaHotel categoriaHotel = categoriaHotelRepository.findById(id)
                .orElseThrow( () -> new NotFoundException("No existe categoria con esta ID"));

        return categoriaHotelMapper.toDto(categoriaHotel);
    }

    @Override
    public CategoriaHotelDTO eliminarCategoria(int id) {
        if(categoriaHotelRepository.checkSiExistenHotelesConCategoria(id))
            throw new CategoriaException("Existen hoteles con esta categoria. No se puede eliminar");

        CategoriaHotel categoriaHotel = categoriaHotelRepository.findById(id)
                .orElseThrow( () -> new NotFoundException("No existe categoria con esta ID"));
        CategoriaHotelDTO categoriaHotelDTO = categoriaHotelMapper.toDto(categoriaHotel);

        categoriaHotelRepository.deleteById(id);

        return categoriaHotelDTO;
    }

    @Override
    public boolean existeCategoriaPorId(int id) {
        return categoriaHotelRepository.existsById(id);
    }

    @Override
    public CategoriaHotelDTO modificarCategoria(CategoriaHotelDTO categoriaHotelDTO) {
        CategoriaHotel categoriaHotel = categoriaHotelRepository.findById(categoriaHotelDTO.getId())
                .orElseThrow( () -> new NotFoundException("No existe categoria con esta ID"));

        categoriaHotelMapper.updateCategoria(categoriaHotel, categoriaHotelMapper.toEntity(categoriaHotelDTO));

        categoriaHotelRepository.save(categoriaHotel);

        return categoriaHotelMapper.toDto(categoriaHotel);
    }

    @Override
    public List<CategoriaHotelDTO> listarTodasCategorias() {
        List<CategoriaHotelDTO> categoriaHotelDTOList = categoriaHotelMapper.toDTOList(categoriaHotelRepository.findAll());
        return categoriaHotelDTOList;
    }
}
