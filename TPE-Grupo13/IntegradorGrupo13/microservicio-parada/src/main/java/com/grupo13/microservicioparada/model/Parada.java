package com.grupo13.microservicioparada.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "parada")
public class Parada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 60)
    private String nombre;
    private String ciudad;
    private String direccion;
    @Column(nullable = false)
    private Double latitud;
    @Column(nullable = false)
    private Double longitud;

    public Parada(String nombre, String ciudad, String direccion, Double latitud, Double longitud) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
