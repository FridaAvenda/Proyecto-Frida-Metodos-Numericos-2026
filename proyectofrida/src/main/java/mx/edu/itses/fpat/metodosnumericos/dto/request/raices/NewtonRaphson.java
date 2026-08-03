package mx.edu.itses.fpat.metodosnumericos.dto.request.raices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewtonRaphson {
    private double xi;
    private String fx;
    private String fxDerivada;
    private double er;
    private int maximoIteraciones;
}
