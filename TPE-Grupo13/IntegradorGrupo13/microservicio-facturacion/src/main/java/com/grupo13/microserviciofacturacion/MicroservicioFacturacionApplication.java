/**
 * 🚀 Clase Principal y Punto de Arranque del Microservicio de Facturación.
 *
 * Esta clase inicia la aplicación Spring Boot.
 * - @SpringBootApplication: Configura y arranca el microservicio.
 * - @EnableFeignClients: Habilita el escaneo de los Feign Clients (UsuarioFeignClient
 * y TarifaFeignClient), permitiendo la comunicación declarativa y síncrona con los
 * microservicios de Usuarios/Cuentas y Tarifas. Esta habilitación es fundamental
 * para el cálculo de importes y la lógica Premium.
 */
package com.grupo13.microserviciofacturacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroservicioFacturacionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioFacturacionApplication.class, args);
    }

}
