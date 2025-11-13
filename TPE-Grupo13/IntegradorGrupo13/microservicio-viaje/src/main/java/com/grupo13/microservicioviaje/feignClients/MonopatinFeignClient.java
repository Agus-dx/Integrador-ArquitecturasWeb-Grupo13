package com.grupo13.microservicioviaje.feignClients;

import com.grupo13.microservicioviaje.feignModels.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-monopatin", url = "http://localhost:8084/api/monopatines")
public interface MonopatinFeignClient {

    @GetMapping("/{id}")
    Monopatin findById(@PathVariable String id);

    @PatchMapping("/{id}/estado/{estado}")
    Monopatin updateEstado(@PathVariable String id, @PathVariable String estado);
}
