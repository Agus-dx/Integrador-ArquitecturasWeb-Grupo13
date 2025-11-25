/**
 * 📝 DTO de Salida para Reportes de Mantenimiento.
 *
 * Esta clase se utiliza para estructurar y transferir la información mínima
 * necesaria cuando se genera un reporte de monopatines que requieren mantenimiento.
 * Su propósito es:
 * 1. Exponer solo el ID y los kilómetros totales recorridos (kmTotales).
 * 2. Se utiliza en el endpoint 'GET /reportes-mantenimiento/{kmMaximo}' para
 * comunicar de forma limpia qué monopatines superaron el umbral y deben ser revisados.
 */
package com.grupo13.microserviciomonopatin.dtos;

import com.grupo13.microserviciomonopatin.model.Monopatin;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ReporteMantenimientoDTO {
    private String id;
    private int kmTotales;

    public ReporteMantenimientoDTO(Monopatin monopatin) {
        this.id = monopatin.getId();
        this.kmTotales = monopatin.getKmRecorridos();
    }
}
