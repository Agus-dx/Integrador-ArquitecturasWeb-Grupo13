/**
 * 📥 DTO de Solicitud (LoginDTO).
 *
 * Objeto utilizado para mapear el cuerpo de la solicitud POST /api/token.
 * Incluye validaciones básicas (`@NotNull`, `@NotEmpty`) para asegurar que
 * las credenciales sean proporcionadas.
 */
package com.grupo13.microserviciogateway.service.dto.login;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
// DTO PARA LOGIN Y OBTENER TOKEN
public class LoginDTO {

    @NotNull( message = "El usuario es un campo requerido." )
    @NotEmpty( message = "El usuario es un campo requerido." )
    private String username;

    @NotNull( message = "La contraseña es un campo requerido." )
    @NotEmpty( message = "La contraseña es un campo requerido." )
    private String password;

    public String toString(){
        return "Username: " + username + ", Password: [FORBIDDEN] ";
    }
}
