package ar.com.juanferrara.GestionHotelera.business.service.impl;

import ar.com.juanferrara.GestionHotelera.business.mapper.impl.CategoriaHabitacionMapper;
import ar.com.juanferrara.GestionHotelera.business.service.CategoriaHabitacionService;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.CategoriaException;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.NotFoundException;
import ar.com.juanferrara.GestionHotelera.domain.dto.CategoriaHabitacionDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.CategoriaHabitacion;
import ar.com.juanferrara.GestionHotelera.persistence.CategoriaHabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaHabitacionServiceImpl implements CategoriaHabitacionService {

    @Autowired
    private CategoriaHabitacionRepository categoriaHabitacionRepository;

    @Autowired
    private CategoriaHabitacionMapper categoriaHabitacionMapper;



    @Override
    public CategoriaHabitacionDTO crearCategoria(CategoriaHabitacionDTO categoriaHabitacionDTO) {
        CategoriaHabitacion categoriaHabitacion = categoriaHabitacionMapper.toEntity(categoriaHabitacionDTO);

        return categoriaHabitacionMapper.toDto(categoriaHabitacionRepository.save(categoriaHabitacion));
    }

    @Override
    public CategoriaHabitacionDTO buscarCategoriaPorId(int id) {
        CategoriaHabitacion categoriaHabitacion = categoriaHabitacionRepository.findById(id)
                .orElseThrow( () -> new NotFoundException("No existe categoria con esta ID"));

        return categoriaHabitacionMapper.toDto(categoriaHabitacion);
    }

    @Override
    public CategoriaHabitacionDTO eliminarCategoria(int id) {
        if(categoriaHabitacionRepository.checkSiExistenHabitacionesConCategoria(id))
            throw new CategoriaException("Existen habitaciones con esta categoria. No se puede eliminar");

        CategoriaHabitacion categoriaHabitacion = categoriaHabitacionRepository.findById(id)
                .orElseThrow( () -> new NotFoundException("No existe categoria con esta ID"));
        CategoriaHabitacionDTO categoriaHabitacionDTO = categoriaHabitacionMapper.toDto(categoriaHabitacion);

        categoriaHabitacionRepository.deleteById(id);

        return categoriaHabitacionDTO;
    }

    @Override
    public CategoriaHabitacionDTO modificarCategoria(CategoriaHabitacionDTO categoriaHabitacionDTO) {
        CategoriaHabitacion categoriaHabitacion = categoriaHabitacionRepository.findById(categoriaHabitacionDTO.getId())
                .orElseThrow( () -> new NotFoundException("No existe categoria con esta ID"));

        categoriaHabitacionMapper.updateEntity(categoriaHabitacion, categoriaHabitacionMapper.toEntity(categoriaHabitacionDTO));

        categoriaHabitacionRepository.save(categoriaHabitacion);

        return categoriaHabitacionMapper.toDto(categoriaHabitacion);
    }

    @Override
    public boolean existeCategoriaPorId(int id) {
        return categoriaHabitacionRepository.existsById(id);
    }

    @Override
    public List<CategoriaHabitacionDTO> listarTodasCategorias() {
        List<CategoriaHabitacionDTO> categoriaHabitacionDTOList = categoriaHabitacionMapper.toDTOList(categoriaHabitacionRepository.findAll());
        return categoriaHabitacionDTOList;
    }
}
