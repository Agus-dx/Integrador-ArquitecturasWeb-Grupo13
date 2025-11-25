/**
 * 🚀 Clase Principal y Punto de Arranque del Microservicio de Tarifas.
 *
 * Esta clase contiene el método 'main' que inicia la aplicación Spring Boot.
 * La anotación @SpringBootApplication combina la configuración, la auto-configuración
 * y el escaneo de componentes. Es el punto de partida del servicio que gestiona
 * la lógica de precios y recargos, siendo esencial para el cálculo de costos en
 * el Microservicio de Viajes.
 */
package com.grupo13.microserviciotarifa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroservicioTarifaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioTarifaApplication.class, args);
    }

}
