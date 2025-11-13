package com.grupo13.microservicioviaje.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "viaje")
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long paradaOrigen;
    @Column(nullable = false)
    private Long paradaDestino;
    @Column(nullable = false)
    private long idTarifa;
    @Column(nullable = false)
    private String idMonopatin;
    @Column(nullable = false)
    private Long idUsuario;
    @Column(nullable = false)
    private Long idCuenta;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private int tiempoTotalMinutos;
    private int kilometrosRecorridos;
    private int tiempoPausa;
    private boolean activo;

    public Viaje(Long paradaOrigen, Long paradaDestino, Long idUsuario, Long idCuenta, String idMonopatin, Long idTarifa) {
        this.paradaOrigen = paradaOrigen;
        this.paradaDestino = paradaDestino;
        this.idUsuario = idUsuario;
        this.idCuenta = idCuenta;
        this.idTarifa = idTarifa;
        this.idMonopatin = idMonopatin;
        this.fechaFin = null;
        this.tiempoTotalMinutos = 0;
        this.kilometrosRecorridos = 0;
        this.tiempoPausa = 0;
        this.activo = true;
    }

    @PrePersist
    protected void onCreate() {
        if (fechaInicio == null) {
            fechaInicio = LocalDateTime.now();
        }
    }
}
