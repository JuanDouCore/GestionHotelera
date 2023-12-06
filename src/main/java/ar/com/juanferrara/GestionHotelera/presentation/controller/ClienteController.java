package ar.com.juanferrara.GestionHotelera.presentation.controller;

import ar.com.juanferrara.GestionHotelera.business.service.ClienteService;
import ar.com.juanferrara.GestionHotelera.domain.dto.usuarios.ClienteDTO;
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

@Tag(name = "7. Cliente", description = "API para la gestión de clientes")

@RestController
@RequestMapping("/api/clientes")

@PreAuthorize("hasRole('ROLE_EMPLEADO') || hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_GERENTE')")
@SecurityRequirement(name = "Bearer Authentication")

@Validated
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    @Operation(summary = "Crear un cliente", description = "Crea un cliente")
    @ApiResponse(responseCode = "201", description = "Cliente creado")

    @PostMapping
    public ResponseEntity<ClienteDTO> crearCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO clienteCreado = clienteService.crearCliente(clienteDTO);
        return ResponseEntity.created(URI.create("/api/clientes/" + clienteCreado.getDni())).body(clienteCreado);
    }


    @Operation(summary = "Listar todos los clientes", description = "Lista todos los clientes")
    @ApiResponse(responseCode = "200", description = "Clientes listados")

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodosClientes() {
        return  ResponseEntity.ok(clienteService.listarTodosLosClientes());
    }

    @Operation(summary = "Buscar un cliente por dni", description = "Busca un cliente por dni")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado")

    @DeleteMapping("{dni}")
    public ResponseEntity<ClienteDTO> eliminarCliente(@PathVariable int dni) {
        return ResponseEntity.ok(clienteService.eliminarCliente(dni));
    }

    @Operation(summary = "Modificar un cliente por dni", description = "Modifica un cliente por dni")
    @ApiResponse(responseCode = "200", description = "Cliente modificado")

    @PatchMapping("{dni}")
    public ResponseEntity<ClienteDTO> modificarCliente(@PathVariable int dni, @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO clienteModificado = clienteService.modificarCliente(dni, clienteDTO);
        return ResponseEntity.ok(clienteModificado);
    }
}
