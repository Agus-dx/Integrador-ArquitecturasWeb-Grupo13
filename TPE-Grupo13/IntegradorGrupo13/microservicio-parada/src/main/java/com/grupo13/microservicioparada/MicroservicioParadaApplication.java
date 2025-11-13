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
