/**
 * 🔗 Cliente Feign para el Microservicio de Monopatines.
 *
 * Esta interfaz define los contratos de comunicación HTTP síncrona
 * con el Microservicio de Monopatines, permitiendo al Microservicio de Paradas
 * obtener información de inventario en tiempo real.
 * * Configuración Clave:
 * 1. @FeignClient(name = "microservicio-monopatin"): Nombre lógico del servicio
 * de destino.
 * 2. url = "http://localhost:8084/api/monopatines": Define la URL base del
 * servicio externo (debe coincidir con la configuración del Monopatín Service).
 * 3. Métodos: Define las llamadas para obtener todos los monopatines en una parada
 * y, más críticamente, solo los monopatines **LIBRES** en esa parada, información
 * necesaria para iniciar un alquiler o mostrar disponibilidad.
 */
package com.grupo13.microservicioparada.feignClients;

import com.grupo13.microservicioparada.feignModel.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "microservicio-monopatin", url = "http://localhost:8084/api/monopatines")
public interface MonopatinFeignClient {

    @GetMapping("/parada/{idParada}")
    List<Monopatin> findMonopatinesByIdParada(@PathVariable Long idParada);

    @GetMapping("/parada/{idParada}?estado=LIBRE")
    List<Monopatin> findMonopatinesLibresByIdParada(@PathVariable Long idParada);
}
