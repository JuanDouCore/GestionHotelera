package ar.com.juanferrara.GestionHotelera.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "refresh_token")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "expiryDate", nullable = false)
    private Instant expiryDate;
}
