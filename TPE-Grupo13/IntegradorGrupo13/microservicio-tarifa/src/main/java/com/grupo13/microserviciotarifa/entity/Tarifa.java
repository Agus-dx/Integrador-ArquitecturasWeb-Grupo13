/**
 * 🏛️ Entidad JPA (Modelo de Dominio) para la tabla 'Tarifa'.
 *
 * Define la estructura completa de los precios y recargos aplicables al servicio
 * de alquiler de monopatines. Es la entidad central de este microservicio.
 * Contiene todos los parámetros necesarios para que el Microservicio de Viajes
 * calcule el costo final de un alquiler, incluyendo:
 * 1. Montos base (monto, montoExtra).
 * 2. Lógica de Pausas (tiempoMaximoPausaMinutos, porcentajeRecargoPausa).
 * 3. Costos de suscripción (cuotaMensualPremium).
 * 4. Fecha de vigencia (fecha).
 */
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
