package mx.edu.itses.fpat.metodosnumericos.service;

import mx.edu.itses.fpat.metodosnumericos.dto.request.raices.Biseccion;
import mx.edu.itses.fpat.metodosnumericos.dto.request.raices.NewtonRaphson;
import mx.edu.itses.fpat.metodosnumericos.dto.request.raices.PuntoFijo;
import mx.edu.itses.fpat.metodosnumericos.dto.request.raices.ReglaFalsa;
import mx.edu.itses.fpat.metodosnumericos.dto.request.raices.Secante;
import mx.edu.itses.fpat.metodosnumericos.dto.request.raices.SecanteModificado;
import mx.edu.itses.fpat.metodosnumericos.dto.response.raices.BiseccionRespuesta;
import mx.edu.itses.fpat.metodosnumericos.dto.response.raices.NewtonRaphsonRespuesta;
import mx.edu.itses.fpat.metodosnumericos.dto.response.raices.PuntoFijoRespuesta;
import mx.edu.itses.fpat.metodosnumericos.dto.response.raices.ReglaFalsaRespuesta;
import mx.edu.itses.fpat.metodosnumericos.dto.response.raices.SecanteModificadoRespuesta;
import mx.edu.itses.fpat.metodosnumericos.dto.response.raices.SecanteRespuesta;
import java.util.List;

// Cambiamos "class" por "interface" según el requerimiento 3.6
public interface RaicesEcuaciones {
    
    // Método que recibe el modelo Biseccion y regresa la lista de respuestas
    List<BiseccionRespuesta> biseccion(Biseccion request);

    // Fase 5.6 Método que recibe el modelo ReglaFalsa y regresa la lista de respuestas
    List<ReglaFalsaRespuesta> reglaFalsa(ReglaFalsa request);

    // Fase 7.6 Método que recibe el modelo PuntoFijo y regresa la lista de respuestas
    List<PuntoFijoRespuesta> puntoFijo(PuntoFijo request);

    // Fase 9.6 Método que recibe el modelo NewtonRaphson y regresa la lista de respuestas
    List<NewtonRaphsonRespuesta> newtonRaphson(NewtonRaphson request);

    // Fase 11.6 Método que recibe el modelo Secante y regresa la lista de respuestas
    List<SecanteRespuesta> secante(Secante request);

    // Fase 13.6 Método que recibe el modelo SecanteModificado y regresa la lista de respuestas
    List<SecanteModificadoRespuesta> secanteModificado(SecanteModificado request);
    
}
