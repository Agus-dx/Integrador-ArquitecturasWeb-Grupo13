package com.grupo13.microservicioadmin.feignClients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="microservicio-facturacion", url="http://localhost:8083/api/facturacion")
public interface FacturacionFeignClient {

    // EN PROCESO

}
