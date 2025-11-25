/**
 * 🛴 Clase Modelo (Documento) para la colección 'monopatines' en MongoDB.
 *
 * Define la estructura de datos del monopatín como un activo móvil.
 * Aspectos clave:
 * 1. Persistencia NoSQL: Utiliza @Document(collection = "monopatines") y
 * @Id (de Spring Data) para mapear el objeto a un documento en MongoDB. El ID
 * es de tipo String, típico de los identificadores generados por Mongo.
 * 2. Atributos Dinámicos: Almacena el estado, la ubicación (latitud/longitud)
 * y las métricas de uso (kmRecorridos, tiempoUsado). Estos campos son de
 * alta frecuencia de actualización.
 * 3. Referencia Lógica: Incluye 'idParada' como una clave externa lógica
 * (no forzada por la DB) para indicar la última estación donde se encuentra el monopatin.
 */
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
