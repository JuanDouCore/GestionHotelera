package ar.com.juanferrara.GestionHotelera.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "direcciones")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Direccion {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "localidad")
    private String localidad;

    @Column(name = "provincia")
    private String provincia;
}
