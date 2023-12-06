package ar.com.juanferrara.GestionHotelera.domain.entity;

import ar.com.juanferrara.GestionHotelera.domain.enums.EstadoReservacion;
import ar.com.juanferrara.GestionHotelera.domain.enums.TipoPension;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "reservaciones")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Reserva {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_hotel")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "id_habitacion")
    private Habitacion habitacion;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoReservacion estadoReservacion;

    @Column(name = "costo_total")
    private double costoTotal;

    @Column(name = "cantidad_abonada")
    private double cantidadAbonada;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_egreso")
    private Date fechaEgreso;

    @Enumerated(EnumType.STRING)
    @Column(name = "pension")
    private TipoPension tipoPension;
}
