/**
 * 🧑‍💻 DTO de Respuesta para el Reporte de Actividad de Usuarios.
 *
 * Esta clase es un Data Transfer Object (DTO) utilizado para encapsular
 * los resultados de las consultas de reportes que miden la actividad de los
 * usuarios a lo largo de un período (como las consultas en ViajeRepository.java).
 * Su propósito es transportar métricas de rendimiento clave por usuario:
 * ID del usuario, cantidad de viajes realizados, total de kilómetros recorridos
 * y tiempo total de uso (en minutos).
 */
package com.grupo13.microservicioviaje.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReporteViajeUsuariosDTO {
    private Long idUsuario;
    private Long cantViajes,cantKmTotales,tiempoTotal;
}
