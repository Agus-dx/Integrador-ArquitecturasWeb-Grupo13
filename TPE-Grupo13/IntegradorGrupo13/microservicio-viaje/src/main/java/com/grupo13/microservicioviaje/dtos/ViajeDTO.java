package com.grupo13.microservicioviaje.dtos;

import com.grupo13.microservicioviaje.model.Viaje;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class ViajeDTO {
    private Long paradaOrigen;
    private Long paradaDestino;
    private String idMonopatin;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Integer tiempoTotalMinutos;
    private Integer kilometrosRecorridos;
    private boolean activo;

    public ViajeDTO(Viaje viaje){
        this.paradaOrigen = viaje.getParadaOrigen();
        this.paradaDestino = viaje.getParadaDestino();
        this.idMonopatin = viaje.getIdMonopatin();
        this.fechaInicio = viaje.getFechaInicio();
        this.fechaFin = viaje.getFechaFin();
        this.tiempoTotalMinutos = viaje.getTiempoTotalMinutos();
        this.kilometrosRecorridos = viaje.getKilometrosRecorridos();
        this.activo = viaje.isActivo();
    }
}
