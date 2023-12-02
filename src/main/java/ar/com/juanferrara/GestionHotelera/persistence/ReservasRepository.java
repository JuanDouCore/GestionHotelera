package ar.com.juanferrara.GestionHotelera.persistence;

import ar.com.juanferrara.GestionHotelera.domain.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservasRepository extends JpaRepository<Reserva, Integer> {

    @Query("SELECT COUNT(r.id)>0 FROM Reserva r " +
            "WHERE r.habitacion.numero = :nroHabitacion " +
            "AND r.hotel.id = :idHotel " +
            "AND :fechaIngreso < r.fechaEgreso " +
            "AND :fechaEgreso >= r.fechaIngreso")
    boolean verificarSiHabitacionEstaDisponible(int idHotel, int nroHabitacion, Date fechaIngreso, Date fechaEgreso);

    @Query("SELECT r FROM Reserva r " +
            "WHERE r.hotel.id = :idHotel " +
            "AND r.fechaIngreso <= :fechaEgreso " +
            "AND r.fechaEgreso > :fechaIngreso")
    List<Reserva> listarReservasPorHotelIdAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(int idHotel, Date fechaIngreso, Date fechaEgreso);

    @Query("SELECT r FROM Reserva r " +
            "WHERE r.hotel.id = :idHotel")
    List<Reserva> listarReservasDeHotel(int idHotel);

    @Query("SELECT r FROM Reserva r " +
            "WHERE r.cliente.dni = :dniCliente")
    List<Reserva> listarReservasDeCliente(int dniCliente);
}
