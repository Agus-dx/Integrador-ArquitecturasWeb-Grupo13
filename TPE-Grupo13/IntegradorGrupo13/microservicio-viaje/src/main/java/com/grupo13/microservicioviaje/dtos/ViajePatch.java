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
