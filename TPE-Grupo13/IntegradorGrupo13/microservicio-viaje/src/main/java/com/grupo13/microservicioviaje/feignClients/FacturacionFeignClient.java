package com.grupo13.microservicioviaje.feignClients;

import com.grupo13.microservicioviaje.feignModels.Factura;
import com.grupo13.microservicioviaje.feignModels.FacturaEmitida;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient (name = "microservicio-facturacion", url = "http://localhost:8083/api/facturas")
public interface FacturacionFeignClient {

    @PostMapping("/viaje")
    FacturaEmitida generarFactura(@RequestBody Factura factura);
}
