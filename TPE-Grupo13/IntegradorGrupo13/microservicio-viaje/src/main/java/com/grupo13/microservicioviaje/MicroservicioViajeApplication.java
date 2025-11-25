/**
 * 🚀 Clase Principal y Punto de Arranque del Microservicio de Viajes.
 *
 * Esta clase contiene el método 'main' que inicia la aplicación Spring Boot.
 * La anotación @SpringBootApplication combina @Configuration, @EnableAutoConfiguration
 * y @ComponentScan. La anotación clave para la arquitectura es
 * @EnableFeignClients, que activa la búsqueda y el registro de todos los
 * Clientes Feign definidos en el proyecto, permitiendo la comunicación declarativa
 * con otros microservicios.
 */
package com.grupo13.microservicioviaje;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroservicioViajeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioViajeApplication.class, args);
    }

}
