package com.grupo13.microservicioadmin.feignClients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="microservicio-tarifas", url="http://localhost:8086/api/tarifas")
public interface TarifasFeignClient {

    // EN PROCESO

}
