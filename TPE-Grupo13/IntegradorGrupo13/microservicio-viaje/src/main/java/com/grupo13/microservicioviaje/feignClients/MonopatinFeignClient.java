/**
 * 🛵 Cliente Feign para el Microservicio de Monopatines.
 *
 * Esta interfaz define el contrato de comunicación con el Microservicio de Monopatines
 * (microservicio-monopatin). Es esencial para la lógica de inicio y finalización de un viaje,
 * permitiendo al Microservicio de Viajes:
 * 1. Consultar el estado y la ubicación de un Monopatín específico (findById).
 * 2. Cambiar el estado del Monopatín (e.g., de 'LIBRE' a 'ACTIVO', o de 'ACTIVO' a 'LIBRE')
 * durante las transacciones de inicio y fin de viaje (updateEstado).
 */
package com.grupo13.microservicioviaje.feignClients;

import com.grupo13.microservicioviaje.feignModels.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-monopatin", url = "http://localhost:8084/api/monopatines")
public interface MonopatinFeignClient {

    @GetMapping("/{id}")
    Monopatin findById(@PathVariable String id);

    @PatchMapping("/{id}/estado/{estado}")
    Monopatin updateEstado(@PathVariable String id, @PathVariable String estado);
}
