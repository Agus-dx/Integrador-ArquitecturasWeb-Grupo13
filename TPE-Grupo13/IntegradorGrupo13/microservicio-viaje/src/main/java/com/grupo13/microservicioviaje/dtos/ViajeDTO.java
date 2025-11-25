/**
 * 📤 DTO Principal de Respuesta (Data Transfer Object) para la entidad Viaje.
 *
 * Esta clase se utiliza para serializar y deserializar la información de los viajes
 * que se comunica entre el Microservicio de Viajes y los clientes externos (o
 * la capa de Controlador). Su propósito es exponer solo los campos relevantes
 * del modelo de dominio 'Viaje' (ocultando IDs internos, costos o campos sensibles)
 * y facilitar la conversión desde la entidad JPA (a través de su constructor).
 */
package com.grupo13.microservicioviaje.dtos;

import com.grupo13.microservicioviaje.model.Viaje;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class ViajeDTO {
    private Long paradaOrigen;
    private Long paradaDestino;
    private String idMonopatin;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Integer tiempoTotalMinutos;
    private Integer kilometrosRecorridos;
    private boolean activo;

    public ViajeDTO(Viaje viaje){
        this.paradaOrigen = viaje.getParadaOrigen();
        this.paradaDestino = viaje.getParadaDestino();
        this.idMonopatin = viaje.getIdMonopatin();
        this.fechaInicio = viaje.getFechaInicio();
        this.fechaFin = viaje.getFechaFin();
        this.tiempoTotalMinutos = viaje.getTiempoTotalMinutos();
        this.kilometrosRecorridos = viaje.getKilometrosRecorridos();
        this.activo = viaje.isActivo();
    }
}
