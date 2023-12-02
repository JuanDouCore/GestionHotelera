package ar.com.juanferrara.GestionHotelera.business.service;

import ar.com.juanferrara.GestionHotelera.domain.dto.usuarios.ClienteDTO;

import java.util.List;

public interface ClienteService {

    ClienteDTO crearCliente(ClienteDTO clienteDTO);
    ClienteDTO buscarClientePorDni(int dni);
    ClienteDTO eliminarCliente(int dni);
    ClienteDTO modificarCliente(int dni, ClienteDTO clienteDTO);
    boolean existeClientePorDni(int dni);
    List<ClienteDTO> listarTodosLosClientes();

}
