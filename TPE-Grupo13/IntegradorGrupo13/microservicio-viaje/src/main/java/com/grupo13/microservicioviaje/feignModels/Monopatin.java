/**
 * 🛵 Modelo Feign (o DTO de Comunicación) para la entidad Monopatin.
 *
 * Esta clase representa la información esencial de un monopatín obtenida del
 * Microservicio de Monopatines. Es vital para las transacciones de viaje, ya que
 * contiene el 'estado' actual para verificar disponibilidad al iniciar el viaje
 * y las coordenadas (latitud, longitud) para validar la finalización en la parada
 * correcta. También registra métricas como km y tiempo de uso.
 */
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
