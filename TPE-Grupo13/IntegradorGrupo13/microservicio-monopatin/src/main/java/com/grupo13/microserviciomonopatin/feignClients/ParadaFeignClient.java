/**
 * 🔗 Cliente Feign para el Microservicio de Paradas.
 *
 * Esta interfaz define el contrato de comunicación HTTP síncrona
 * con el Microservicio de Paradas, permitiendo al Monopatín Service verificar
 * la existencia de una Parada por su ID antes de crear o modificar un Monopatín.
 * * * Configuración Clave:
 * 1. @FeignClient(name = "microservicio-parada"): Nombre lógico del servicio
 * de destino.
 * 2. url = "http://localhost:8085/api/paradas": Define la URL base del
 * servicio externo (debe coincidir con la configuración del Parada Service).
 * 3. findById: Método para obtener los detalles de una Parada, vital para
 * validar la clave externa lógica 'idParada' en la entidad Monopatin y
 * para enriquecer las respuestas.
 */
package com.grupo13.microserviciomonopatin.feignClients;

import com.grupo13.microserviciomonopatin.feignModel.Parada;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-parada", url = "http://localhost:8085/api/paradas")
public interface ParadaFeignClient {

    @GetMapping("/{id}")
    Parada findById(@PathVariable Long id);
}
