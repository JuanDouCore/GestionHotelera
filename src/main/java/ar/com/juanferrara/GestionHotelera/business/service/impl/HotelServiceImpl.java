package ar.com.juanferrara.GestionHotelera.business.service.impl;

import ar.com.juanferrara.GestionHotelera.business.mapper.impl.CrearHotelMapper;
import ar.com.juanferrara.GestionHotelera.business.mapper.impl.DireccionMapper;
import ar.com.juanferrara.GestionHotelera.business.mapper.impl.HotelMapper;
import ar.com.juanferrara.GestionHotelera.business.service.CategoriaHotelService;
import ar.com.juanferrara.GestionHotelera.business.service.HotelService;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.HotelException;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.NotFoundException;
import ar.com.juanferrara.GestionHotelera.domain.dto.CrearHotelDTO;
import ar.com.juanferrara.GestionHotelera.domain.dto.HotelDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.Hotel;
import ar.com.juanferrara.GestionHotelera.persistence.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CategoriaHotelService categoriaHotelService;

    @Autowired
    private HotelMapper hotelMapper;
    @Autowired
    private CrearHotelMapper crearHotelMapper;
    @Autowired
    private DireccionMapper direccionMapper;

    @Override
    public HotelDTO crearHotel(CrearHotelDTO crearHotelDTO) {
        HotelDTO hotelDTO = crearHotelMapper.toEntity(crearHotelDTO);
        hotelDTO.setCategoriaHotel(categoriaHotelService.buscarCategoriaPorId(crearHotelDTO.getId_categoria()));

        Hotel hotel = hotelMapper.toEntity(hotelDTO);

        return hotelMapper.toDto(hotelRepository.save(hotel));
    }

    @Override
    public HotelDTO buscarHotelPorId(int id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe hotel con esta ID"));

        return hotelMapper.toDto(hotel);
    }

    @Override
    public HotelDTO eliminarHotel(int id) {
        if(hotelRepository.checkSiExistenHabitacionesConHotel(id))
            throw new HotelException("No se puede eliminar este hotel porque existen habitaciones asignadas a este");

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe hotel con esta ID"));

        hotelRepository.deleteById(id);

        return hotelMapper.toDto(hotel);
    }

    @Override
    public HotelDTO modificarHotel(int id, CrearHotelDTO crearHotelDTO) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe hotel con esta ID"));

        HotelDTO hotelDTO = hotelMapper.toDto(hotel);
        crearHotelMapper.updateEntity(hotelDTO, crearHotelMapper.toEntity(crearHotelDTO));

        hotelDTO.setCategoriaHotel(categoriaHotelService.buscarCategoriaPorId(
                crearHotelDTO.getId_categoria() != 0 ? crearHotelDTO.getId_categoria()
                        : hotel.getCategoriaHotel().getId()
        ));
        if(crearHotelDTO.getDireccion() != null) {
            int idDireccion = hotel.getDireccion().getId();
            direccionMapper.updateDireccion(hotel.getDireccion(), direccionMapper.toEntity(crearHotelDTO.getDireccion()));
            hotelDTO.setDireccion(direccionMapper.toDto(hotel.getDireccion()));
            hotel.getDireccion().setId(idDireccion);
        }

        hotelDTO.setId(id);

        hotelMapper.updateEntity(hotel, hotelMapper.toEntity(hotelDTO));

        hotelRepository.save(hotel);

        return hotelMapper.toDto(hotel);
    }

    @Override
    public boolean existeHotelPorId(int id) {
        return hotelRepository.existsById(id);
    }

    @Override
    public List<HotelDTO> listarTodosHoteles() {
        List<HotelDTO> hotelDTOList = hotelMapper.toDTOList(hotelRepository.findAll());
        return hotelDTOList;
    }
}
