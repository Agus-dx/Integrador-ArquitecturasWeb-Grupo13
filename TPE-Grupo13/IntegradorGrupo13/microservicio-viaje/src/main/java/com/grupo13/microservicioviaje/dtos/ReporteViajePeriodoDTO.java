/**
 * 📊 DTO de Respuesta para el Reporte de Viajes por Período.
 *
 * Esta clase es un Data Transfer Object (DTO) utilizado para encapsular los
 * resultados del reporte generado por la consulta JPQL en ViajeRepository.java
 * (getReporteViajeAnio). Su propósito es transportar de manera estructurada
 * la información sobre el rendimiento de los monopatines, incluyendo:
 * ID del monopatín, la cantidad de viajes que realizó en un año específico.
 */
package com.grupo13.microservicioviaje.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ReporteViajePeriodoDTO {
    private String idMonopatin;
    private Long cantidadViajes;
    private Integer anio;
}
