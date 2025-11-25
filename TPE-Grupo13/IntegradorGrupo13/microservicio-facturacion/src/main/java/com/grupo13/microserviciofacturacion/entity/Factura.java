/**
 * 📝 Entidad JPA (Modelo de Dominio) para la tabla 'factura'.
 *
 * Define la estructura de los registros de cobro generados en el sistema.
 * Es la entidad principal del Microservicio de Facturación.
 * * Aspectos clave:
 * 1. Campos Financieros: Almacena el 'importe' y la 'fechaEmision'.
 * 2. Referencias Lógicas (Claves Externas): Mantiene referencias a IDs de otros
 * microservicios para contextualizar la factura:
 * - usuarioId: ¿Quién pagó? (Microservicio de Usuarios/Cuentas)
 * - viajeId: ¿Qué servicio se facturó? (Microservicio de Viajes)
 * - tarifaId: ¿Qué regla de costo se aplicó? (Microservicio de Tarifas)
 * 3. Identificador Único: 'numeroFactura' está marcado como único, asegurando
 * que cada documento de cobro tenga una referencia fiscal inmutable.
 */
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

