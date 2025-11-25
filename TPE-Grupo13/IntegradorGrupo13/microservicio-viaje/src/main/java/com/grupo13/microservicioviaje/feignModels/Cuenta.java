/**
 * 💳 Modelo Feign (o DTO de Comunicación) para la entidad Cuenta.
 *
 * Esta clase representa la información de la cuenta de usuario obtenida
 * del Microservicio de Cuentas. Es fundamental para la lógica de negocio,
 * ya que permite al Microservicio de Viajes:
 * 1. Verificar el 'saldo' antes de iniciar y finalizar un viaje.
 * 2. Determinar si la cuenta tiene el 'estado' y 'tipoCuenta' adecuados.
 * 3. Gestionar métricas específicas como 'kmConsumidosMes' para posibles beneficios/restricciones.
 */
package com.grupo13.microservicioviaje.feignModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {
    private Long id;
    private BigDecimal saldo;
    private String estado;
    private String tipoCuenta;
    private Double kmConsumidosMes;
    private LocalDate fechaRenovacionCupo;
}
