package mx.edu.itses.fpat.metodosnumericos.dto.request.raices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecanteModificado {
    private double xi;
    private double delta;
    private String fx;
    private double er;
    private int maximoIteraciones;
}
