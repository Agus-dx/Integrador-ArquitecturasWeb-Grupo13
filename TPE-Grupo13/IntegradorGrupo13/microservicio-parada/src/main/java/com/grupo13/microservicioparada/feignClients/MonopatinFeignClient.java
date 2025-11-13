package com.grupo13.microservicioparada.feignClients;

import com.grupo13.microservicioparada.feignModel.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "microservicio-monopatin", url = "http://localhost:8084/api/monopatines")
public interface MonopatinFeignClient {

    @GetMapping("/parada/{idParada}")
    List<Monopatin> findMonopatinesByIdParada(@PathVariable Long idParada);

    @GetMapping("/parada/{idParada}?estado=LIBRE")
    List<Monopatin> findMonopatinesLibresByIdParada(@PathVariable Long idParada);
}
