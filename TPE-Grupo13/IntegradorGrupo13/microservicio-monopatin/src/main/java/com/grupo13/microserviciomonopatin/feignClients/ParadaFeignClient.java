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
