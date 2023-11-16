package ar.com.juanferrara.GestionHotelera.persistence;

import ar.com.juanferrara.GestionHotelera.domain.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    @Query("SELECT count(h)>0 FROM Habitacion h WHERE h.hotel.id = :id")
    boolean checkSiExistenHabitacionesConHotel(int id);
}
