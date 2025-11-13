package com.grupo13.microservicioviaje.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ReporteViajePeriodoDTO {
    private String idMonopatin;
    private int cantidadViajes;
    private int anio;
}
