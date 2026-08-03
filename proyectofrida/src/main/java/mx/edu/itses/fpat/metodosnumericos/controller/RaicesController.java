package mx.edu.itses.fpat.metodosnumericos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
import mx.edu.itses.fpat.metodosnumericos.service.RaicesEcuaciones;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/raices")
@RequiredArgsConstructor // Genera el constructor necesario para la inyección de dependencias automática
public class RaicesController {

    // Capa de Servicio inyectada mediante el constructor de Lombok
    private final RaicesEcuaciones raicesService;

    // 2.3 Direcciona a la raíz de /views/raices/index.html
    @GetMapping
    public String index() {
        return "views/raices/index";
    }

    // 2.4 Métodos cerrados
    @GetMapping("/biseccion")
    public String biseccion(Model model) {
        model.addAttribute("biseccion", new Biseccion());
        // Direcciona la salida hacia form.html
        return "views/raices/biseccion/form"; 
    }

    // Método de procesamiento modificado para la Fase 4
    @PostMapping("/biseccion")
    public String algoritmoBiseccion(@ModelAttribute Biseccion biseccionRequest, Model model) {
        // Imprime en la consola mediante Lombok los valores recibidos
        log.info("Valores recibidos para Bisección:");
        log.info("XL: {}", biseccionRequest.getXl()); 
        log.info("XU: {}", biseccionRequest.getXu()); 
        log.info("FX: {}", biseccionRequest.getFx()); 
        log.info("ER: {}", biseccionRequest.getEr()); 
        log.info("MaximoIteraciones: {}", biseccionRequest.getMaximoIteraciones()); 

        // 1. Invocamos la lógica matemática pasándole los parámetros recibidos
        List<BiseccionRespuesta> resultados = raicesService.biseccion(biseccionRequest);

        // 2. Enviamos la lista de objetos resultantes al modelo de Thymeleaf
        model.addAttribute("resultados", resultados);

        // 3. Direccionamos la salida hacia la nueva plantilla 'solucion.html'
        return "views/raices/biseccion/solucion"; 
    }

    // Fase 5.4 Direcciona a /templates/views/raices/regla-falsa/form.html
    @GetMapping("/regla-falsa")
    public String reglaFalsa(Model model) {
        model.addAttribute("reglaFalsa", new ReglaFalsa());
        return "views/raices/regla-falsa/form";
    }

    // Fase 5.5/6.2 Método de procesamiento de Regla Falsa
    @PostMapping("/regla-falsa")
    public String algoritmoReglaFalsa(@ModelAttribute ReglaFalsa reglaFalsaRequest, Model model) {
        log.info("Valores recibidos para Regla Falsa:");
        log.info("XL: {}", reglaFalsaRequest.getXl());
        log.info("XU: {}", reglaFalsaRequest.getXu());
        log.info("FX: {}", reglaFalsaRequest.getFx());
        log.info("ER: {}", reglaFalsaRequest.getEr());
        log.info("MaximoIteraciones: {}", reglaFalsaRequest.getMaximoIteraciones());

        List<ReglaFalsaRespuesta> resultados = raicesService.reglaFalsa(reglaFalsaRequest);
        model.addAttribute("resultados", resultados);

        return "views/raices/regla-falsa/solucion";
    }

    // Fase 7.4 Direcciona a /templates/views/raices/punto-fijo/form.html
    @GetMapping("/punto-fijo")
    public String puntoFijo(Model model) {
        model.addAttribute("puntoFijo", new PuntoFijo());
        return "views/raices/punto-fijo/form";
    }

    // Fase 7.5/8.2 Método de procesamiento de Iteración de Punto Fijo
    @PostMapping("/punto-fijo")
    public String algoritmoPuntoFijo(@ModelAttribute PuntoFijo puntoFijoRequest, Model model) {
        log.info("Valores recibidos para Iteración de Punto Fijo:");
        log.info("XI: {}", puntoFijoRequest.getXi());
        log.info("GX: {}", puntoFijoRequest.getGx());
        log.info("ER: {}", puntoFijoRequest.getEr());
        log.info("MaximoIteraciones: {}", puntoFijoRequest.getMaximoIteraciones());

        List<PuntoFijoRespuesta> resultados = raicesService.puntoFijo(puntoFijoRequest);
        model.addAttribute("resultados", resultados);

        return "views/raices/punto-fijo/solucion";
    }

    // Fase 9.4 Direcciona a /templates/views/raices/newton-raphson/form.html
    @GetMapping("/newton-raphson")
    public String newtonRaphson(Model model) {
        model.addAttribute("newtonRaphson", new NewtonRaphson());
        return "views/raices/newton-raphson/form";
    }

    // Fase 9.5/10.2 Método de procesamiento de Newton-Raphson
    @PostMapping("/newton-raphson")
    public String algoritmoNewtonRaphson(@ModelAttribute NewtonRaphson newtonRaphsonRequest, Model model) {
        log.info("Valores recibidos para Newton-Raphson:");
        log.info("XI: {}", newtonRaphsonRequest.getXi());
        log.info("FX: {}", newtonRaphsonRequest.getFx());
        log.info("FXDerivada: {}", newtonRaphsonRequest.getFxDerivada());
        log.info("ER: {}", newtonRaphsonRequest.getEr());
        log.info("MaximoIteraciones: {}", newtonRaphsonRequest.getMaximoIteraciones());

        List<NewtonRaphsonRespuesta> resultados = raicesService.newtonRaphson(newtonRaphsonRequest);
        model.addAttribute("resultados", resultados);

        return "views/raices/newton-raphson/solucion";
    }

    // Fase 11.4 Direcciona a /templates/views/raices/secante/form.html
    @GetMapping("/secante")
    public String secante(Model model) {
        model.addAttribute("secante", new Secante());
        return "views/raices/secante/form";
    }

    // Fase 11.5/12.2 Método de procesamiento de la Secante
    @PostMapping("/secante")
    public String algoritmoSecante(@ModelAttribute Secante secanteRequest, Model model) {
        log.info("Valores recibidos para la Secante:");
        log.info("X0: {}", secanteRequest.getX0());
        log.info("X1: {}", secanteRequest.getX1());
        log.info("FX: {}", secanteRequest.getFx());
        log.info("ER: {}", secanteRequest.getEr());
        log.info("MaximoIteraciones: {}", secanteRequest.getMaximoIteraciones());

        List<SecanteRespuesta> resultados = raicesService.secante(secanteRequest);
        model.addAttribute("resultados", resultados);

        return "views/raices/secante/solucion";
    }

    // Fase 13.4 Direcciona a /templates/views/raices/secante-modificado/form.html
    @GetMapping("/secante-modificado")
    public String secanteModificado(Model model) {
        model.addAttribute("secanteModificado", new SecanteModificado());
        return "views/raices/secante-modificado/form";
    }

    // Fase 13.5/14.2 Método de procesamiento de la Secante Modificado
    @PostMapping("/secante-modificado")
    public String algoritmoSecanteModificado(@ModelAttribute SecanteModificado secanteModificadoRequest, Model model) {
        log.info("Valores recibidos para la Secante Modificado:");
        log.info("XI: {}", secanteModificadoRequest.getXi());
        log.info("Delta: {}", secanteModificadoRequest.getDelta());
        log.info("FX: {}", secanteModificadoRequest.getFx());
        log.info("ER: {}", secanteModificadoRequest.getEr());
        log.info("MaximoIteraciones: {}", secanteModificadoRequest.getMaximoIteraciones());

        List<SecanteModificadoRespuesta> resultados = raicesService.secanteModificado(secanteModificadoRequest);
        model.addAttribute("resultados", resultados);

        return "views/raices/secante-modificado/solucion";
    }
}
