/**
 * 📍 Entidad JPA (Modelo de Dominio) para la tabla 'parada'.
 *
 * Define la estructura de los puntos geográficos donde los usuarios pueden
 * iniciar y finalizar los alquileres de monopatines. Es la entidad central
 * de este microservicio.
 * * * Aspectos clave:
 * 1. Almacena la **información geográfica** (latitud y longitud), que es crucial
 * para la lógica de búsqueda de paradas cercanas.
 * 2. Los campos de geolocalización están marcados como @Column(nullable = false),
 * asegurando la integridad de los datos para los cálculos de distancia (Fórmula
 * del Haversine).
 */
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
