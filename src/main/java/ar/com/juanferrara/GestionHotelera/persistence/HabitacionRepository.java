package ar.com.juanferrara.GestionHotelera.persistence;

import ar.com.juanferrara.GestionHotelera.domain.entity.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {

    @Query("SELECT h FROM Habitacion h WHERE h.hotel.id = :idHotel AND h.numero = :nroHabitacion")
    Optional<Habitacion> findByNroAndHotel(int idHotel, int nroHabitacion);

    @Query("DELETE FROM Habitacion h WHERE h.hotel.id = :idHotel AND h.numero = :nroHabitacion")
    void deleteByNroAndHotel(int idHotel, int nroHabitacion);

    @Query("SELECT COALESCE(MAX(h.numero), 0) + 1 FROM Habitacion h WHERE h.hotel.id = :idHotel")
    int obtenerProximoNroParaHabitacionDeHotel(int idHotel);

    @Query("SELECT count(h)>0 FROM Habitacion h WHERE h.hotel.id = :idHotel AND h.numero = :nroHabitacion")
    boolean existByNroAndHotel(int idHotel, int nroHabitacion);

    @Query("SELECT h FROM Habitacion h WHERE h.hotel.id = :idHotel")
    List<Habitacion> obtenerHabitacionesDeUnHotel(int idHotel);

    @Query("SELECT h FROM Habitacion h WHERE h.hotel.id = :idHotel AND h.categoriaHabitacion.id = :idCategoria")
    List<Habitacion> obtenerHabitacionesDeUnaCategoriaDeUnHotel(int idHotel, int idCategoria);

    @Query("SELECT count(r)>0 FROM Reserva r WHERE r.hotel.id = :idHotel AND r.habitacion.numero = :nroHabitacion")
    boolean checkSiExistenReservasConUnaHabitacion(int idHotel, int nroHabitacion);
}
