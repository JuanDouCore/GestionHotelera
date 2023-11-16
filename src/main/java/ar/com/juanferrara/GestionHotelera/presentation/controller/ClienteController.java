package ar.com.juanferrara.GestionHotelera.presentation.controller;

import ar.com.juanferrara.GestionHotelera.business.service.ClienteService;
import ar.com.juanferrara.GestionHotelera.domain.dto.ClienteDTO;
import ar.com.juanferrara.GestionHotelera.domain.enums.EstadoReservacion;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Validated
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDTO> crearCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO clienteCreado = clienteService.crearCliente(clienteDTO);
        return ResponseEntity.created(URI.create("/api/clientes/" + clienteCreado.getDni())).body(clienteCreado);
    }

    @GetMapping("{dni}/{test}")
    public ResponseEntity<ClienteDTO> getCliente(@PathVariable int dni, @PathVariable EstadoReservacion test) {
        return ResponseEntity.ok(clienteService.buscarClientePorDni(dni));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodosClientes() {
        return  ResponseEntity.ok(clienteService.listarTodosLosClientes());
    }

    @DeleteMapping("{dni}")
    public ResponseEntity<ClienteDTO> eliminarCliente(@PathVariable int dni) {
        return ResponseEntity.ok(clienteService.eliminarCliente(dni));
    }

    @PatchMapping("{dni}")
    public ResponseEntity<ClienteDTO> modificarCliente(@PathVariable int dni, @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO clienteModificado = clienteService.modificarCliente(dni, clienteDTO);
        return ResponseEntity.ok(clienteModificado);
    }
}
