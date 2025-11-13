package com.grupo13.microservicioviaje.feignClients;

import com.grupo13.microservicioviaje.feignModels.Tarifa;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-tarifas", url = "http://localhost:8086/tarifas")
public interface TarifaFeignClient {

    @GetMapping("/{id}")
    Tarifa findById(@PathVariable Long id);
}
