package com.grupo13.microserviciotarifa.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "tarifa")
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double monto;
    private Double montoExtra;
    private Date fecha;
    private Integer tiempoMaximoPausaMinutos;
    private Double porcentajeRecargoPausa;
    private Double cuotaMensualPremium;
}
