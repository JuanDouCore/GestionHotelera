package ar.com.juanferrara.GestionHotelera.domain.dto.reservas;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class CriterioReservaHabitacionDTO {
    //(Date fechaInicio, Date fechaFin, int categoriaHabitacion, int ocupantes, int camas)
    private Date fechaInicio;
    private Date fechaFin;
    @Positive
    private int categoriaHabitacion;
    @Positive
    private int ocupantes;
    @Positive
    private int camas;
}
