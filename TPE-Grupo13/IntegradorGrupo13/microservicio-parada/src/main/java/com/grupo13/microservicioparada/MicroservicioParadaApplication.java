/**
 * 🚀 Clase Principal y Punto de Arranque del Microservicio de Paradas.
 *
 * Esta clase inicia la aplicación Spring Boot.
 * - @SpringBootApplication: Configura y arranca el microservicio.
 * - @EnableFeignClients: **Habilita el escaneo de interfaces marcadas con @FeignClient**
 * (como MonopatinFeignClient), permitiendo la comunicación declarativa y síncrona
 * con el Microservicio de Monopatines. Esta anotación es fundamental para la
 * funcionalidad de orquestación de inventario de paradas.
 */
package com.grupo13.microservicioparada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroservicioParadaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioParadaApplication.class, args);
    }

}
