package com.grupo13.microserviciofacturacion.feignClient;

import com.grupo13.microserviciofacturacion.dto.TarifaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-tarifa", url = "http://localhost:8086/api/tarifas")
public interface TarifaFeignClient {

    @GetMapping("/")
    ResponseEntity<TarifaDTO[]> findAllTarifas();

    @GetMapping("/{id}")
    ResponseEntity<TarifaDTO> findTarifaById(@PathVariable Long id);
}
