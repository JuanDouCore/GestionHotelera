package ar.com.juanferrara.GestionHotelera;

import ar.com.juanferrara.GestionHotelera.domain.entity.Usuario;
import ar.com.juanferrara.GestionHotelera.domain.enums.Role;
import ar.com.juanferrara.GestionHotelera.persistence.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class GestionHoteleraApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionHoteleraApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			Usuario usuario = Usuario.builder()
					.dni(44560065)
					.nombre("Juan")
					.username("juanferrara")
					.idHotelAsignado(1)
					.nombre("GRAN ALVEAR")
					.role(Role.GERENTE)
					.password(passwordEncoder.encode("hola123"))
					.build();

			usuarioRepository.save(usuario);
		};
		}

}
