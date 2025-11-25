/**
 * 💳 Cliente Feign para el Microservicio de Cuentas.
 *
 * Esta interfaz define el contrato de comunicación con el Microservicio de Cuentas
 * (microservicio-usuario-cuenta). Sus funciones principales son:
 * 1. Consultar el estado y el saldo de una cuenta específica (findById),
 * esencial para las validaciones pre-viaje y post-viaje.
 * 2. Actualizar el saldo de la cuenta restando el costo total del viaje
 * después de que la factura ha sido generada (restarSaldo), asegurando
 * la integridad de la transacción económica.
 */
package com.grupo13.microservicioviaje.feignClients;

import com.grupo13.microservicioviaje.feignModels.Cuenta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "microservicio-usuario-cuenta", url = "http://localhost:8082/api/cuentas")
public interface CuentaFeignClient {

    @GetMapping("/{id}")
    Cuenta findById(@PathVariable Long id);

    @PutMapping("/restar-saldo/{id}")
    Cuenta restarSaldo(@PathVariable Long id, @RequestParam(name = "montoRestar") BigDecimal resta);
}
