/**
 * 🧾 Cliente Feign para el Microservicio de Facturación.
 *
 * Esta interfaz define el contrato de comunicación con el Microservicio de Facturación
 * (microservicio-facturacion). Su única y crucial función es iniciar el proceso
 * de cobro: recibe el DTO de solicitud 'Factura' con los detalles del viaje finalizado
 * y solicita la generación de la factura. Devuelve 'FacturaEmitida' si la operación
 * es exitosa, lo cual sirve como validación para que el Microservicio de Viajes
 * proceda a restar el saldo de la cuenta del usuario.
 */
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
