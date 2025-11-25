/**
 * 🚦 Enumeración (Enum) para el Estado de la Cuenta.
 *
 * Define los posibles estados en los que puede encontrarse una Cuenta en el sistema.
 * Esta enumeración se utiliza en la entidad Cuenta.java para controlar si una
 * cuenta es válida para realizar transacciones (ACTIVA) o si su uso debe ser
 * denegado (SUSPENDIDA), lo cual es crucial para la lógica de negocio y seguridad.
 */
package com.grupo13.microserviciousuario.entity;

public enum EstadoCuenta {
    ACTIVA,
    SUSPENDIDA
}
