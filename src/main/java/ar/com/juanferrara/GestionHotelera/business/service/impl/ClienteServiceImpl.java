package ar.com.juanferrara.GestionHotelera.business.service.impl;

import ar.com.juanferrara.GestionHotelera.business.mapper.impl.ClienteMapper;
import ar.com.juanferrara.GestionHotelera.business.mapper.impl.DireccionMapper;
import ar.com.juanferrara.GestionHotelera.business.service.ClienteService;
import ar.com.juanferrara.GestionHotelera.domain.dto.usuarios.ClienteDTO;
import ar.com.juanferrara.GestionHotelera.domain.entity.Cliente;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.ClienteException;
import ar.com.juanferrara.GestionHotelera.domain.exceptions.NotFoundException;
import ar.com.juanferrara.GestionHotelera.persistence.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;
    @Autowired
    private DireccionMapper direccionMapper;


    @Override
    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        if(existeClientePorDni(clienteDTO.getDni()))
            throw new ClienteException("Ya existe un cliente con este DNI");

        Cliente cliente = clienteMapper.toEntity(clienteDTO);

        return clienteMapper.toDto(clienteRepository.save(cliente));
    }

    @Override
    public ClienteDTO buscarClientePorDni(int dni) {
        Cliente cliente = clienteRepository.findById(dni)
                .orElseThrow(() -> new NotFoundException("No existe cliente con este DNI"));

        return clienteMapper.toDto(cliente);
    }

    @Override
    public ClienteDTO eliminarCliente(int dni) {
        if(clienteRepository.checkSiExistenReservasConEsteCliente(dni))
            throw new ClienteException("No se puede eliminar este cliente debido que posee reservas asociadas");

        Cliente cliente = clienteRepository.findById(dni)
                .orElseThrow(() -> new NotFoundException("No existe cliente con este DNI"));

        clienteRepository.deleteById(dni);

        return clienteMapper.toDto(cliente);
    }

    @Override
    public ClienteDTO modificarCliente(int dni, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(dni)
                .orElseThrow(() -> new NotFoundException("No existe cliente con este DNI"));

        clienteMapper.updateCliente(cliente, clienteMapper.toEntity(clienteDTO));
        if(clienteDTO.getDireccion() != null) {
            int idDireccion = cliente.getDireccion().getId();
            direccionMapper.updateDireccion(cliente.getDireccion(), direccionMapper.toEntity(clienteDTO.getDireccion()));
            cliente.getDireccion().setId(idDireccion);
        }

        cliente.setDni(dni);

        clienteRepository.save(cliente);

        return clienteMapper.toDto(cliente);
    }

    @Override
    public boolean existeClientePorDni(int dni) {
        return clienteRepository.existsById(dni);
    }

    @Override
    public List<ClienteDTO> listarTodosLosClientes() {
        return clienteMapper.toDTOList(clienteRepository.findAll());
    }


}
