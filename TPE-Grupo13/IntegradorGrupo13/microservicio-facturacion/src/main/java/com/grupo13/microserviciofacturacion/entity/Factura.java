package com.grupo13.microserviciofacturacion.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table (name = "factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_factura", nullable = false, unique = true)
    private String numeroFactura;

    @Column(name = "fecha_emision")
    private Date fechaEmision;

    private double importe;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "viaje_id")
    private Long viajeId;

    @Column(name = "tarifa_id")
    private Long tarifaId;
}

