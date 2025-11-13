package com.grupo13.microserviciomonopatin.dtos;

import com.grupo13.microserviciomonopatin.model.Monopatin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class MonopatinDTO {
    private String id;
    private String estado;
    private double latitud;
    private double longitud;
    private int kmRecorridos;
    private int tiempoUsado;

    public MonopatinDTO(Monopatin monopatin) {
        this.id = monopatin.getId();
        this.estado = monopatin.getEstado().toString();
        this.latitud = monopatin.getLatitud();
        this.longitud = monopatin.getLongitud();
        this.kmRecorridos = monopatin.getKmRecorridos();
        this.tiempoUsado = monopatin.getTiempoUsado();
    }
}
