package ar.com.juanferrara.GestionHotelera.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "habitaciones")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Habitacion {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_hotel")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriaHabitacion categoriaHabitacion;

    @Column(name = "numero")
    private int numero;

    @Column(name = "precio")
    private double precio;

    @Column(name = "camas")
    private int camas;

    @Column(name = "capacidad")
    private int capacidad;
}
