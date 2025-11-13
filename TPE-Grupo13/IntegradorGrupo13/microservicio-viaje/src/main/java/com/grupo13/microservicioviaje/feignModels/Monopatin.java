package com.grupo13.microservicioviaje.feignModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Monopatin {
    private String id;
    private String estado;
    private double latitud;
    private double longitud;
    private int kmRecorridos;
    private int tiempoUsado;
}
