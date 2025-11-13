package com.grupo13.microservicioparada.dtos;

public record ParadaPatchDTO(
        String nombre,
        String ciudad,
        String direccion,
        Double latitud,
        Double longitud
) {
}
