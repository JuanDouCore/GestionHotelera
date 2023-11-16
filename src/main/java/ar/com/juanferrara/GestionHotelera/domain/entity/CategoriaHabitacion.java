package ar.com.juanferrara.GestionHotelera.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categoria_habitacion")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CategoriaHabitacion {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;
}
