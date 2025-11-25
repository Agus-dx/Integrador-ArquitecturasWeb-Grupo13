/**
 * 📝 DTO de Solicitud (UserDTO).
 *
 * Objeto utilizado para mapear el cuerpo de la solicitud POST /api/registrar.
 * Representa los datos necesarios para crear un nuevo usuario.
 */
package com.grupo13.microserviciogateway.service.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
// DTO PARA CREAR UN USUARIO
public class UserDTO {

    @NotNull( message = "El email es un campo requerido." )
    @NotEmpty( message = "El email es un campo requerido." )
    private String email;

    @NotNull( message = "La password es un campo requerido." )
    @NotEmpty( message = "La password es un campo requerido." )
    private String password;

    @NotNull( message = "El nombre es un campo requerido." )
    @NotEmpty( message = "El nombre es un campo requerido." )
    private String nombre;

    @NotNull( message = "El apellido es un campo requerido." )
    @NotEmpty( message = "El apellido es un campo requerido." )
    private String apellido;

    @NotNull( message = "El rol es un campo requerido." )
    @NotEmpty( message = "El rol es un campo requerido." )
    private String rol;
}
