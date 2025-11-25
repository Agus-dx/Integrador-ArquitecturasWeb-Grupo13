/**
 * 📍 Cliente Feign para el Microservicio de Paradas.
 *
 * Esta interfaz define el contrato de comunicación con el Microservicio de Paradas
 * (microservicio-parada). Su función principal es obtener los detalles de ubicación
 * de las paradas (coordenadas geográficas y descripción) para que el Microservicio
 * de Viajes pueda validar la existencia de las paradas de origen y destino,
 * y confirmar la ubicación final del monopatín al terminar el alquiler.
 */
package com.grupo13.microservicioviaje.feignClients;

import com.grupo13.microservicioviaje.feignModels.Parada;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-parada", url = "http://localhost:8085/api/paradas")
public interface ParadaFeignClient {

    @GetMapping("/{id}")
    Parada findById(@PathVariable Long id);
}
