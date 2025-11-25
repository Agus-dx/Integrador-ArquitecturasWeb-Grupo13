/**
 * 🧾 Modelo Feign (o DTO de Comunicación) para la Factura Emitida.
 *
 * Esta clase es utilizada para deserializar la respuesta del Microservicio de Facturación
 * al finalizar un viaje. Representa el comprobante de pago final generado, que incluye
 * el número de factura, la fecha de emisión y el importe cobrado, sirviendo como prueba
 * de la transacción completada en el sistema.
 */
package com.grupo13.microservicioviaje.feignModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaEmitida {
    private Long id;
    private String numeroFactura;
    private Date fechaEmision;
    private double importe;
    private Long usuarioId;
    private Long tarifaId;
    private Long viajeId;
}
