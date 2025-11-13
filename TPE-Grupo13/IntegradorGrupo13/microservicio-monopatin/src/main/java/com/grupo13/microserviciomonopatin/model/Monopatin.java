package com.grupo13.microserviciomonopatin.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Document(collection = "monopatines")
public class Monopatin {
    @Id
    private String id;
    private Estado estado;
    private double latitud;
    private double longitud;
    private int kmRecorridos;
    private int tiempoUsado;
    private Long idParada;

    public Monopatin(Estado estado, double latitud, double longitud,Long idParada) {
        this.estado = estado;
        this.latitud = latitud;
        this.longitud = longitud;
        this.kmRecorridos = 0;
        this.tiempoUsado = 0;
        this.idParada = idParada;
    }

    public Monopatin(double latitud, double longitud,Long idParada) {
        this.estado = Estado.LIBRE;
        this.latitud = latitud;
        this.longitud = longitud;
        this.kmRecorridos = 0;
        this.tiempoUsado = 0;
        this.idParada = idParada;
    }
}
