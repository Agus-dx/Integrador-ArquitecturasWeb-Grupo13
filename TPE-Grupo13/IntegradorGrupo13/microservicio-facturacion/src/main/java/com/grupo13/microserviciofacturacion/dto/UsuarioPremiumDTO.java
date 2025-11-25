/**
 * 🧑‍💻 DTO de Entrada/Modelo de Datos Externo para Usuario Premium.
 *
 * Esta clase NO es una entidad de persistencia en este microservicio. Es el
 * objeto que el Facturación Service espera recibir cuando consulta al
 * Microservicio de Usuarios/Cuentas a través del Feign Client.
 * * Propósito:
 * 1. Definir Contrato: Asegura que la Facturación Service reciba los datos
 * específicos del plan Premium de un usuario.
 * 2. Lógica de Cobro: Contiene los datos críticos necesarios para aplicar
 * la lógica de descuento en el Service Layer:
 * - esPremium: Confirma si aplica el descuento.
 * - cupoMensualKm: El límite de KM gratuitos.
 * - kmConsumidosMes: Cuántos KM ha usado el usuario este mes.
 * Estos datos permiten al Facturación Service determinar si el viaje actual
 * es gratuito, parcial o si se debe aplicar la tarifa con descuento del 50%.
 */
package com.grupo13.microserviciofacturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPremiumDTO {
    private Long id;
    private String nombre;
    private boolean esPremium;
    private Double cupoMensualKm;
    private Double kmConsumidosMes;
    private Integer mesActual;
    private Integer anioActual;
}
