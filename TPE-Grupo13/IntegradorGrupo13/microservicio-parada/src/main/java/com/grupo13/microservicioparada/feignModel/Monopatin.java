/**
 * 📦 Modelo de Datos Externo (Feign Model/Client Model) para Monopatín.
 *
 * Esta clase NO es una entidad JPA, sino un modelo de objeto utilizado para
 * deserializar la respuesta JSON recibida desde el **Microservicio de Monopatines** * a través del Feign Client.
 * Su propósito es permitir al Microservicio de Paradas manejar temporalmente la
 * información esencial de los monopatines (estado, ubicación, métricas) para
 * poder incluirla en sus propias respuestas (como en ParadaDTO).
 */
package com.grupo13.microservicioparada.feignModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Monopatin {
    private String estado;
    private double latitud;
    private double longitud;
    private int kmRecorridos;
    private int tiempoUsado;
}
