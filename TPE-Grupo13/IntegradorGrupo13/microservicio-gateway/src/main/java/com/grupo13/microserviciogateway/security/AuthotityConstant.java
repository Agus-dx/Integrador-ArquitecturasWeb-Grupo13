/**
 * 🏷️ Constantes de Roles (AuthotityConstant).
 *
 * Clase simple que centraliza las definiciones de los roles (Autoridades)
 * que se utilizan para la autorización en `SecurityConfig`. Esto evita errores
 * tipográficos en la lógica de seguridad.
 */
package com.grupo13.microserviciogateway.security;

public final class AuthotityConstant {

    private AuthotityConstant() {}

    public static final String _ADMIN = "ADMIN";
    public static final String _USUARIO = "USUARIO";
    public static final String _MANTENIMIENTO = "MANTENIMIENTO";
}
