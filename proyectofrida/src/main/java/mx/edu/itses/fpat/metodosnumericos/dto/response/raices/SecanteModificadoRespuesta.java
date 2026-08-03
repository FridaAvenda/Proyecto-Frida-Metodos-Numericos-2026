package mx.edu.itses.fpat.metodosnumericos.dto.response.raices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecanteModificadoRespuesta {
    private int iteracion;
    private double xi;
    private double deltaXi;
    private double fxi;
    private double fxiDelta;
    private double xiSiguiente;
    private double er;
}
