package mx.edu.itses.fpat.metodosnumericos.dto.response.raices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecanteRespuesta {
    private int iteracion;
    private double x0;
    private double x1;
    private double fx0;
    private double fx1;
    private double x2;
    private double er;
}
