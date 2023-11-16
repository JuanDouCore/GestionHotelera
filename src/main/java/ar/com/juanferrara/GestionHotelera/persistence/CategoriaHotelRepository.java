package ar.com.juanferrara.GestionHotelera.persistence;

import ar.com.juanferrara.GestionHotelera.domain.entity.CategoriaHabitacion;
import ar.com.juanferrara.GestionHotelera.domain.entity.CategoriaHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaHotelRepository extends JpaRepository<CategoriaHotel, Integer> {

    @Query("SELECT count(h)>0 FROM Hotel h WHERE h.categoriaHotel.id = :id")
    boolean checkSiExistenHotelesConCategoria(int id);

}
