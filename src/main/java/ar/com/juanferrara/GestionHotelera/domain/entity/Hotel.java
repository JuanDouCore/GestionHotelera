package ar.com.juanferrara.GestionHotelera.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hoteles")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Hotel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_direccion")
    private Direccion direccion;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriaHotel categoriaHotel;
}
