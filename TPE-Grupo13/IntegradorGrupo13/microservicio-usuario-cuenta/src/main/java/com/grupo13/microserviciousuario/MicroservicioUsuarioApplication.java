/**
 * 🚀 Clase Principal y Punto de Arranque del Microservicio de Usuarios y Cuentas.
 *
 * Esta clase contiene el método 'main' que inicia la aplicación Spring Boot.
 * La anotación @SpringBootApplication combina la configuración, la auto-configuración
 * y el escaneo de componentes. Es la puerta de entrada para la ejecución de
 * todo el código de las capas Controller, Service, Repository y Entity definidas
 * en este microservicio.
 */
package com.grupo13.microserviciousuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroservicioUsuarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioUsuarioApplication.class, args);
    }

}
