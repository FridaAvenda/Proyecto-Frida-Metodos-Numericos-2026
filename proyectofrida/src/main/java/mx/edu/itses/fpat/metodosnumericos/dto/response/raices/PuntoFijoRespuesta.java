package mx.edu.itses.fpat.metodosnumericos.dto.response.raices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PuntoFijoRespuesta {
    private int iteracion;
    private double xi;
    private double gxi;
    private double er;
}
