/**
 * 🏷️ Enumeración (Enum) para el Tipo de Cuenta.
 *
 * Define las categorías de servicio que una Cuenta puede tener (BASICA o PREMIUM).
 * Esta clasificación es fundamental para la lógica de negocio y facturación, ya que:
 * 1. El tipo PREMIUM puede implicar tarifas especiales o beneficios (como cupos de KM gratis),
 * manejados en la entidad Cuenta.java.
 * 2. La lógica de cálculo de costos en el Microservicio de Viajes podría variar
 * basándose en este tipo de cuenta.
 */
package com.grupo13.microserviciousuario.entity;

public enum TipoCuenta {
    BASICA,
    PREMIUM
}
