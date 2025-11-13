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
