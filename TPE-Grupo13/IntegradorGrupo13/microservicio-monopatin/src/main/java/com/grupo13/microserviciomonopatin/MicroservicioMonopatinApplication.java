/**
 * 🚀 Clase Principal y Punto de Arranque del Microservicio de Monopatines.
 *
 * Esta clase inicia la aplicación Spring Boot.
 * - @SpringBootApplication: Configura y arranca el microservicio.
 * - @EnableFeignClients: **Habilita el escaneo de interfaces marcadas con @FeignClient**
 * (como ParadaFeignClient), permitiendo la comunicación declarativa y síncrona
 * con el Microservicio de Paradas. Esta anotación es fundamental para que el
 * servicio pueda validar y enriquecer las referencias a las paradas.
 */
package com.grupo13.microserviciomonopatin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroservicioMonopatinApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioMonopatinApplication.class, args);
    }

}
