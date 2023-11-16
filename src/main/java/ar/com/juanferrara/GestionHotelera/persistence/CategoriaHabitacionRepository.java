package ar.com.juanferrara.GestionHotelera.persistence;

import ar.com.juanferrara.GestionHotelera.domain.entity.CategoriaHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaHabitacionRepository extends JpaRepository<CategoriaHabitacion, Integer> {

    @Query("SELECT count(h)>0 FROM Habitacion h WHERE h.categoriaHabitacion.id = :id")
    boolean checkSiExistenHabitacionesConCategoria(int id);
}
