/**
 * 💰 Cliente Feign para el Microservicio de Tarifas.
 *
 * Esta interfaz define el contrato de comunicación con el Microservicio de Tarifas
 * (microservicio-tarifas). Su función principal es obtener la estructura de
 * precios y recargos (monto base, monto extra, porcentaje de recargo por pausa,
 * etc.) necesarios para que el Microservicio de Viajes pueda calcular el costo
 * final del alquiler durante el proceso de finalización del viaje.
 */
package com.grupo13.microservicioviaje.feignClients;

import com.grupo13.microservicioviaje.feignModels.Tarifa;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-tarifas", url = "http://localhost:8086/tarifas")
public interface TarifaFeignClient {

    @GetMapping("/{id}")
    Tarifa findTarifaById(@PathVariable Long id);
}
