package mx.edu.itses.fpat.metodosnumericos.dto.response.raices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewtonRaphsonRespuesta {
    private int iteracion;
    private double xi;
    private double fxi;
    private double fxiDerivada;
    private double xiSiguiente;
    private double er;
}
