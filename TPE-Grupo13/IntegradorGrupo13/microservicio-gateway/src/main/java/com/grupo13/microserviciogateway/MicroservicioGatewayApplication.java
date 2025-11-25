/**
 * 🚀 Clase Principal y Punto de Arranque del Microservicio de Gateway.
 *
 * Esta clase inicializa el API Gateway.
 * - @SpringBootApplication: Configura y arranca el microservicio.
 * - @EnableDiscoveryClient: Habilita el cliente de descubrimiento de servicios.
 * Esto permite que el Gateway se registre automáticamente en el **Servidor de
 * Descubrimiento (Eureka o similar)** y, más importante, pueda **descubrir
 * la ubicación** (IP:Puerto) de los otros microservicios (Viajes, Monopatines,
 * Facturación, etc.) para poder rutear las peticiones entrantes.
 */
package com.grupo13.microserviciogateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroservicioGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioGatewayApplication.class, args);
    }

}
