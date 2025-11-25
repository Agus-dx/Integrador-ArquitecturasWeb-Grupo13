/**
 * 💸 DTO de Entrada/Modelo de Datos Externo para Tarifa.
 *
 * Esta clase NO es una entidad de persistencia en este microservicio. Es el
 * objeto que el Facturación Service espera recibir cuando consulta al
 * Microservicio de Tarifas a través del Feign Client.
 * * Propósito:
 * 1. Definir Contrato: Asegura que la Facturación Service sepa qué datos
 * esperar para el cálculo de costos.
 * 2. Campos Clave: Contiene todos los parámetros necesarios para la lógica
 * de cobro:
 * - monto (tarifa variable por KM).
 * - montoExtra (tarifa fija por viaje).
 * - reglas de recargo (tiempoMaximoPausaMinutos, porcentajeRecargoPausa).
 * - cuotaMensualPremium (relevante para el servicio de Usuarios/Cuentas).
 */
package com.grupo13.microserviciofacturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TarifaDTO {
    private Long id;
    private double monto;
    private double montoExtra;
    private Date date;
    private Integer tiempoMaximoPausaMinutos;
    private Double porcentajeRecargoPausa;
    private Double cuotaMensualPremium;

    @Override
    public String toString() {
        return "TarifaDTO [monto=" + monto +
                ", montoExtra=" + montoExtra +
                ", tiempoMaximoPausaMinutos=" + tiempoMaximoPausaMinutos +
                ", porcentajeRecargoPausa=" + porcentajeRecargoPausa +
                ", cuotaMensualPremium=" + cuotaMensualPremium +
                ", date=" + date + "]";
    }
}
