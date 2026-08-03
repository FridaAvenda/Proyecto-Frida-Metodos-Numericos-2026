package mx.edu.itses.fpat.metodosnumericos.dto.request.raices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PuntoFijo {
    private double xi;
    private String gx;
    private double er;
    private int maximoIteraciones;
}
