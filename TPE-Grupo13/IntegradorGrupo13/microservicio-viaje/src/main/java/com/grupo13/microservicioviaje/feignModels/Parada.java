/**
 * 📍 Modelo Feign (o DTO de Comunicación) para la entidad Parada.
 *
 * Esta clase sirve como una representación local de la entidad 'Parada'
 * que reside en otro microservicio (probablemente el Microservicio de Monopatín/Ubicación).
 * Contiene la información geográfica y descriptiva clave (latitud, longitud, dirección)
 * necesaria para validar la ubicación de inicio y fin de un viaje.
 */
package com.grupo13.microservicioviaje.feignModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Parada {
    private String nombre;
    private String ciudad;
    private String direccion;
    private Double latitud;
    private Double longitud;
}
