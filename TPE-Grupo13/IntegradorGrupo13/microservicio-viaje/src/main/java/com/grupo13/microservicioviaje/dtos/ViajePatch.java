/**
 * 🔄 DTO de Solicitud (Request DTO) para Actualizaciones Parciales (PATCH).
 *
 * Esta clase es un Data Transfer Object (DTO) definido como un Java Record,
 * utilizado específicamente para manejar las peticiones HTTP de tipo PATCH.
 * Su propósito es permitir que el cliente envíe solo una o varias de las
 * propiedades del viaje que desea modificar, sin tener que enviar el objeto
 * completo. Al ser un Record, es inmutable y conciso, ideal para este tipo de operaciones.
 */
package com.grupo13.microservicioviaje.dtos;

import java.time.LocalDateTime;

public record ViajePatch(
        Long paradaOrigen,
        Long paradaDestino,
        Long idTarifa,
        String idMonopatin,
        Long idUsuario,
        Long idCuenta,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        Integer tiempoTotalMinutos,
        Integer kilometrosRecorridos,
        Boolean activo
) {
}
