/**
 * 📤 DTO de Respuesta de Usuario (UserResponse).
 *
 * Modelo de datos que el Gateway espera recibir del Microservicio de Usuarios/Cuentas.
 * Es crucial que contenga la **contraseña hasheada** y el **rol** para que el
 * proceso de autenticación de Spring Security pueda completarse.
 */
package com.grupo13.microserviciogateway.feignModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// LO QUE ME DEVUELVE CADA PETICION EN EL FEIGN
public class UserResponse {
    private Long id;
    private String username;
    private String password;
    private String rol;
}
