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
