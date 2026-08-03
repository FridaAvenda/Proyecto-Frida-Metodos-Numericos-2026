package mx.edu.itses.fpat.metodosnumericos.service.impl;

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
import org.matheclipse.core.eval.ExprEvaluator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service 
public class RaicesEcuacionesImplementation implements RaicesEcuaciones {

    @Override
    public List<BiseccionRespuesta> biseccion(Biseccion request) {
        log.info("Iniciando servicio de cálculo de Bisección...");
        
        List<BiseccionRespuesta> iteraciones = new ArrayList<>();
        ExprEvaluator util = new ExprEvaluator(); // Motor matemático Symja
        
        double xl = request.getXl();
        double xu = request.getXu();
        String fx = request.getFx();
        double tolerancia = request.getEr();
        int maxIter = request.getMaximoIteraciones();
        
        double fxl = evaluarFuncion(util, fx, xl);
        double fxu = evaluarFuncion(util, fx, xu);
        
        // Evaluar si existe una raíz en el intervalo
        if (fxl * fxu >= 0) {
            log.warn("No se garantiza una raíz en el intervalo dado.");
            return iteraciones; 
        }

        double xrAnterior = 0;
        double erActual = 100.0; 
        int iteracionActual = 1;

        // Ciclo repetitivo y criterio de convergencia
        while (erActual > tolerancia && iteracionActual <= maxIter) {
            double xrActual = (xl + xu) / 2.0;
            double fxr = evaluarFuncion(util, fx, xrActual);

            if (iteracionActual > 1) {
                if (xrActual == 0) {
                    erActual = 100.0;
                } else {
                    erActual = Math.abs((xrActual - xrAnterior) / xrActual) * 100.0;
                }
            }

            // Usamos el Builder de Lombok para crear la respuesta de esta iteración
            BiseccionRespuesta respuesta = BiseccionRespuesta.builder()
                    .iteracion(iteracionActual)
                    .xl(xl)
                    .xu(xu)
                    .xr(xrActual)
                    .fx(fx)
                    .fxl(fxl)
                    .fxu(fxu)
                    .fxr(fxr)
                    .er(erActual)
                    .build();
            iteraciones.add(respuesta);

            // Evaluar subintervalos
            double producto = fxl * fxr;
            if (producto < 0) {
                xu = xrActual;
                fxu = fxr; 
            } else if (producto > 0) {
                xl = xrActual;
                fxl = fxr; 
            } else {
                erActual = 0; // Raíz exacta encontrada
            }

            xrAnterior = xrActual;
            iteracionActual++;
        }

        return iteraciones; 
    }

    @Override
    public List<ReglaFalsaRespuesta> reglaFalsa(ReglaFalsa request) {
        log.info("Iniciando servicio de cálculo de Regla Falsa...");

        List<ReglaFalsaRespuesta> iteraciones = new ArrayList<>();
        ExprEvaluator util = new ExprEvaluator();

        double xl = request.getXl();
        double xu = request.getXu();
        String fx = request.getFx();
        double tolerancia = request.getEr();
        int maxIter = request.getMaximoIteraciones();

        double fxl = evaluarFuncion(util, fx, xl);
        double fxu = evaluarFuncion(util, fx, xu);

        // Fase 6.2 paso 2: criterio F(XL) * F(XU) < 0
        if (fxl * fxu >= 0) {
            log.warn("No se garantiza una raíz en el intervalo dado.");
            return iteraciones;
        }

        double xrAnterior = 0;
        double erActual = 100.0;
        int iteracionActual = 1;

        while (erActual > tolerancia && iteracionActual <= maxIter) {
            // Fase 6.2 paso 3: validación de división por cero
            if (fxl == fxu) {
                log.warn("División por cero: F(XL) es igual a F(XU).");
                break;
            }
            double xrActual = xu - ((fxu * (xl - xu)) / (fxl - fxu));

            // Fase 6.2 paso 4: evaluar F(XR)
            double fxr = evaluarFuncion(util, fx, xrActual);

            // Fase 6.2 paso 6: cálculo de ER
            if (iteracionActual > 1) {
                if (xrActual == 0) {
                    erActual = 100.0;
                } else {
                    erActual = Math.abs((xrActual - xrAnterior) / xrActual) * 100.0;
                }
            }

            // Fase 6.2 paso 7: almacenar los valores de la iteración
            ReglaFalsaRespuesta respuesta = ReglaFalsaRespuesta.builder()
                    .iteracion(iteracionActual)
                    .xl(xl)
                    .xu(xu)
                    .xr(xrActual)
                    .fx(fx)
                    .fxl(fxl)
                    .fxu(fxu)
                    .fxr(fxr)
                    .er(erActual)
                    .build();
            iteraciones.add(respuesta);

            // Fase 6.2 paso 5: determinar las condiciones
            double producto = fxl * fxr;
            if (producto < 0) {
                xu = xrActual;
                fxu = fxr;
            } else if (producto > 0) {
                xl = xrActual;
                fxl = fxr;
            } else {
                erActual = 0; // Raíz exacta encontrada
            }

            xrAnterior = xrActual;
            iteracionActual++;
        }

        return iteraciones;
    }

    @Override
    public List<PuntoFijoRespuesta> puntoFijo(PuntoFijo request) {
        log.info("Iniciando servicio de cálculo de Iteración de Punto Fijo...");

        List<PuntoFijoRespuesta> iteraciones = new ArrayList<>();
        ExprEvaluator util = new ExprEvaluator();

        double xi = request.getXi();
        String gx = request.getGx();
        double tolerancia = request.getEr();
        int maxIter = request.getMaximoIteraciones();

        int iteracionActual = 1;
        double erActual = 100.0;

        while (erActual > tolerancia && iteracionActual <= maxIter) {
            // Fase 8.2 paso 1: evaluar GX en XI
            double gxi = evaluarFuncion(util, gx, xi);
            // Fase 8.2 paso 2: asignar XISiguiente = G(XI)
            double xiSiguiente = gxi;

            // Fase 8.2 paso 3: cálculo de ER
            if (iteracionActual > 1) {
                if (xiSiguiente == 0) {
                    erActual = 100.0;
                } else {
                    erActual = Math.abs((xiSiguiente - xi) / xiSiguiente) * 100.0;
                }
            }

            // Fase 8.2 paso 4: almacenar los valores de la iteración
            PuntoFijoRespuesta respuesta = PuntoFijoRespuesta.builder()
                    .iteracion(iteracionActual)
                    .xi(xi)
                    .gxi(gxi)
                    .er(erActual)
                    .build();
            iteraciones.add(respuesta);

            // Fase 8.2 paso 6: si no converge, asignar XI = XISiguiente
            xi = xiSiguiente;
            iteracionActual++;
        }

        return iteraciones;
    }

    @Override
    public List<NewtonRaphsonRespuesta> newtonRaphson(NewtonRaphson request) {
        log.info("Iniciando servicio de cálculo de Newton-Raphson...");

        List<NewtonRaphsonRespuesta> iteraciones = new ArrayList<>();
        ExprEvaluator util = new ExprEvaluator();

        double xi = request.getXi();
        String fx = request.getFx();
        String fxDerivada = request.getFxDerivada();
        double tolerancia = request.getEr();
        int maxIter = request.getMaximoIteraciones();

        int iteracionActual = 1;
        double erActual = 100.0;

        while (erActual > tolerancia && iteracionActual <= maxIter) {
            // Fase 10.2 paso 1: evaluar FX y FXDerivada en XI
            double fxi = evaluarFuncion(util, fx, xi);
            double fxiDerivada = evaluarFuncion(util, fxDerivada, xi);

            // Fase 10.2 paso 2: validar que F'(XI) != 0
            if (fxiDerivada == 0) {
                log.warn("División por cero: F'(XI) es igual a 0.");
                break;
            }

            // Fase 10.2 paso 3: calcular XISiguiente
            double xiSiguiente = xi - (fxi / fxiDerivada);

            // Fase 10.2 paso 4: cálculo de ER
            if (iteracionActual > 1) {
                if (xiSiguiente == 0) {
                    erActual = 100.0;
                } else {
                    erActual = Math.abs((xiSiguiente - xi) / xiSiguiente) * 100.0;
                }
            }

            // Fase 10.2 paso 5: almacenar los valores de la iteración
            NewtonRaphsonRespuesta respuesta = NewtonRaphsonRespuesta.builder()
                    .iteracion(iteracionActual)
                    .xi(xi)
                    .fxi(fxi)
                    .fxiDerivada(fxiDerivada)
                    .xiSiguiente(xiSiguiente)
                    .er(erActual)
                    .build();
            iteraciones.add(respuesta);

            // Fase 10.2 paso 6: criterio de convergencia; si no, XI = XISiguiente
            xi = xiSiguiente;
            iteracionActual++;
        }

        return iteraciones;
    }

    @Override
    public List<SecanteRespuesta> secante(Secante request) {
        log.info("Iniciando servicio de cálculo de la Secante...");

        List<SecanteRespuesta> iteraciones = new ArrayList<>();
        ExprEvaluator util = new ExprEvaluator();

        double x0 = request.getX0();
        double x1 = request.getX1();
        String fx = request.getFx();
        double tolerancia = request.getEr();
        int maxIter = request.getMaximoIteraciones();

        int iteracionActual = 1;
        double erActual = 100.0;

        while (erActual > tolerancia && iteracionActual <= maxIter) {
            // Fase 12.2 paso 1: evaluar FX en X0 y X1
            double fx0 = evaluarFuncion(util, fx, x0);
            double fx1 = evaluarFuncion(util, fx, x1);

            // Fase 12.2 paso 2: validar que F(X0) - F(X1) != 0
            if (fx0 == fx1) {
                log.warn("División por cero: F(X0) es igual a F(X1).");
                break;
            }

            // Fase 12.2 paso 3: calcular X2
            double x2 = x1 - ((fx1 * (x0 - x1)) / (fx0 - fx1));

            // Fase 12.2 paso 4: cálculo de ER
            if (iteracionActual > 1) {
                if (x2 == 0) {
                    erActual = 100.0;
                } else {
                    erActual = Math.abs((x2 - x1) / x2) * 100.0;
                }
            }

            // Fase 12.2 paso 5: almacenar los valores de la iteración
            SecanteRespuesta respuesta = SecanteRespuesta.builder()
                    .iteracion(iteracionActual)
                    .x0(x0)
                    .x1(x1)
                    .fx0(fx0)
                    .fx1(fx1)
                    .x2(x2)
                    .er(erActual)
                    .build();
            iteraciones.add(respuesta);

            // Fase 12.2 paso 6: reasignar puntos X0 = X1, X1 = X2
            x0 = x1;
            x1 = x2;
            iteracionActual++;
        }

        return iteraciones;
    }

    @Override
    public List<SecanteModificadoRespuesta> secanteModificado(SecanteModificado request) {
        log.info("Iniciando servicio de cálculo de la Secante Modificado...");

        List<SecanteModificadoRespuesta> iteraciones = new ArrayList<>();
        ExprEvaluator util = new ExprEvaluator();

        double xi = request.getXi();
        double delta = request.getDelta();
        String fx = request.getFx();
        double tolerancia = request.getEr();
        int maxIter = request.getMaximoIteraciones();

        int iteracionActual = 1;
        double erActual = 100.0;

        while (erActual > tolerancia && iteracionActual <= maxIter) {
            // Fase 14.2 paso 1: calcular el punto perturbado XI_Delta = XI + (Delta * XI)
            double deltaXi = delta * xi;
            double xiDelta = xi + deltaXi;

            // Fase 14.2 paso 2: evaluar FX en XI y en XI_Delta
            double fxi = evaluarFuncion(util, fx, xi);
            double fxiDelta = evaluarFuncion(util, fx, xiDelta);

            // Fase 14.2 paso 3: validar que F(XI_Delta) - F(XI) != 0
            if (fxiDelta == fxi) {
                log.warn("División por cero: F(XI_Delta) es igual a F(XI).");
                break;
            }

            // Fase 14.2 paso 4: calcular XISiguiente
            double xiSiguiente = xi - ((deltaXi * fxi) / (fxiDelta - fxi));

            // Fase 14.2 paso 5: cálculo de ER
            if (iteracionActual > 1) {
                if (xiSiguiente == 0) {
                    erActual = 100.0;
                } else {
                    erActual = Math.abs((xiSiguiente - xi) / xiSiguiente) * 100.0;
                }
            }

            // Fase 14.2 paso 6: almacenar los valores de la iteración
            SecanteModificadoRespuesta respuesta = SecanteModificadoRespuesta.builder()
                    .iteracion(iteracionActual)
                    .xi(xi)
                    .deltaXi(deltaXi)
                    .fxi(fxi)
                    .fxiDelta(fxiDelta)
                    .xiSiguiente(xiSiguiente)
                    .er(erActual)
                    .build();
            iteraciones.add(respuesta);

            // Fase 14.2 paso 7: criterio de convergencia; si no, XI = XISiguiente
            xi = xiSiguiente;
            iteracionActual++;
        }

        return iteraciones;
    }

    private double evaluarFuncion(ExprEvaluator util, String funcion, double valorX) {
        String expresionAValidar = funcion.replaceAll("(?<![a-zA-Z])x(?![a-zA-Z])", String.valueOf(valorX));
        return util.evalf(expresionAValidar);
    }
}
