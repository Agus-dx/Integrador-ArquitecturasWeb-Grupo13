/**
 * 📈 DTO de Salida para Reportes de Total Facturado.
 *
 * Esta clase se utiliza para estructurar la respuesta del endpoint
 * 'GET /total-facturado', que calcula la suma total de los importes
 * de las facturas dentro de un rango específico de meses y un año.
 * * Propósito:
 * 1. Contexto del Reporte: Incluye los parámetros de entrada (anio, mesDesde,
 * mesHasta) junto con el resultado ('totalFacturado') para que el cliente
 * sepa exactamente qué periodo representa el total.
 * 2. Simplicidad: Proporciona una vista de alto nivel del rendimiento financiero
 * para el periodo consultado.
 */
package com.grupo13.microserviciofacturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalFacturadoDTO {
    private int anio;
    private int mesDesde;
    private int mesHasta;
    private double totalFacturado;
}
