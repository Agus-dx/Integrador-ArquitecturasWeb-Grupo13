/**
 * 🔒 Modelo de Datos Interno (User) para Spring Security en el Gateway.
 *
 * Esta clase NO es una entidad de persistencia. Su función es modelar los
 * datos de un usuario de manera que sean compatibles con el flujo de **Spring
 * Security** dentro del API Gateway.
 * * Propósito:
 * 1. Mapeo de Seguridad: Recibe un DTO/respuesta (`UserResponse`) del
 * Microservicio de Usuarios/Cuentas (que autentica al usuario).
 * 2. Generación de Autoridades: Convierte el campo `rol` del servicio de
 * Usuarios en el formato `authorities` (Set<String>) que Spring Security
 * utiliza para la **autorización** (qué endpoints puede acceder).
 */
package com.grupo13.microserviciogateway.security;

import com.grupo13.microserviciogateway.feignModel.UserResponse;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;
    private Set<String> authorities = new HashSet<>();

    public User(UserResponse response) {
        this.id = response.getId();
        this.username = response.getUsername();
        this.password = response.getPassword();
        this.addAuthority(response.getRol());
    }

    public void addAuthority( String authority ) {
        this.authorities.add( authority );
    }
}
