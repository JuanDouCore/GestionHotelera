package ar.com.juanferrara.GestionHotelera.persistence;

import ar.com.juanferrara.GestionHotelera.domain.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ReservasRepository extends JpaRepository<Reserva, Integer> {

    @Query("SELECT COUNT(r.id)>1 FROM Reserva r " +
            "WHERE r.habitacion.numero = :nroHabitacion " +
            "AND r.hotel.id = :idHotel " +
            "AND :fechaIngreso < r.fechaEgreso " +
            "AND :fechaEgreso >= r.fechaIngreso")
    boolean verificarSiHabitacionEstaDisponible(int idHotel, int nroHabitacion, Date fechaIngreso, Date fechaEgreso);
}
