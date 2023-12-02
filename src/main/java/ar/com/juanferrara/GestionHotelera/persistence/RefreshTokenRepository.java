package ar.com.juanferrara.GestionHotelera.persistence;

import ar.com.juanferrara.GestionHotelera.domain.entity.RefreshToken;
import ar.com.juanferrara.GestionHotelera.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    @Query("DELETE FROM RefreshToken rt WHERE rt.usuario = ?1")
    void deleteByUsuarioId(Long idUsuario);
}
