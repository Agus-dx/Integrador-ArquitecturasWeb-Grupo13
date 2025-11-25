/**
 * 👑 Enumeración (Enum) para los Roles de Usuario.
 *
 * Define los diferentes niveles de acceso y permisos que un Usuario puede tener
 * en el sistema. Los roles son utilizados para la gestión de seguridad y para
 * determinar qué operaciones pueden realizar ciertos usuarios (e.g., ADMINISTRADOR
 * o MANTENIMIENTO) en este y potencialmente otros microservicios.
 */
package com.grupo13.microserviciousuario.entity;

public enum Rol
{
    ADMIN,
    USUARIO,
    MANTENIMIENTO;

    public static Rol perteneceAlEnum(String estado) {
        try {
            return Rol.valueOf(estado.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }
}
