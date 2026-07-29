package mx.edu.itses.fpat.metodosnumericos.service;

import mx.edu.itses.fpat.metodosnumericos.dto.request.raices.Biseccion;
import mx.edu.itses.fpat.metodosnumericos.dto.response.raices.BiseccionRespuesta;
import java.util.List;

public interface RaicesService {
    // Método público que regresa un arreglo (o Lista) de objetos BiseccionRespuesta y recibe la clase Biseccion [cite: 174, 175]
    List<BiseccionRespuesta> biseccion(Biseccion request);
}
