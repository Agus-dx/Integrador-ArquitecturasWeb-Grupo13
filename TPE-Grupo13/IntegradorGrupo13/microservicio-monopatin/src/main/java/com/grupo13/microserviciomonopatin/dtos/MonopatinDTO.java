/**
 * 📤 DTO Principal de Respuesta (Data Transfer Object) para la entidad Monopatin.
 *
 * Esta clase se utiliza para estructurar la información esencial de un Monopatín
 * que se envía como respuesta a las peticiones HTTP (GET). Su propósito es:
 * 1. Proteger el Modelo de Dominio: Expone solo los atributos relevantes del
 * Monopatín, sin exponer la entidad de MongoDB directamente.
 * 2. Estandarización de Contrato: Asegura que todos los clientes (internos como
 * el Microservicio de Paradas, o externos) reciban la información del activo
 * en un formato predecible.
 * 3. Conversión de Enum: Convierte el campo 'estado' (que es un Enum en el
 * modelo) a String para su transferencia en JSON.
 */
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
