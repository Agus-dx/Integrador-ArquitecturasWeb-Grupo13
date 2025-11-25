/**
 * 📝 DTO de Salida (FacturaDTO) para el Microservicio de Facturación.
 *
 * Esta clase se utiliza para serializar y exponer información de la Factura
 * a través de la API REST. Su propósito principal es:
 * 1. Limitar la Exposición de Datos: Sólo expone los campos directamente
 * relevantes para el cliente o para otros servicios que solo necesitan saber
 * el resultado del cobro (número de factura, fecha e importe).
 * 2. Oculta Claves Lógicas: Intencionalmente omite los IDs de las referencias
 * cruzadas (usuarioId, viajeId, tarifaId), ya que son detalles internos de la
 * trazabilidad que no siempre son necesarios en la respuesta final al usuario.
 * 3. Uso de Lombok: Utiliza @Data y @Builder para facilitar su construcción
 * y manejo.
 */
package com.grupo13.microserviciofacturacion.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class FacturaDTO {
    private String numeroFactura;
    private String fechaEmision;
    private double importe;

    @Override
    public String toString() {
        return "FacturaDTO{" +
                ", numeroFactura='" + numeroFactura + '\'' +
                ", fechaEmision='" + fechaEmision + '\'' +
                ", importe=" + importe +
                '}';
    }

}
