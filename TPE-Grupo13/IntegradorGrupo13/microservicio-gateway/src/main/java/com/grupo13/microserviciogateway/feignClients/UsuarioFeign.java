/**
 * 🔗 Cliente Feign (UsuarioFeign).
 *
 * Interfaz declarativa que permite al Gateway comunicarse con el Microservicio
 * de Usuarios/Cuentas (que se ejecuta en el puerto 8082).
 * * Métodos Clave:
 * 1. `findUserByUsername`: Utilizado por `DomainUserDetailsService` para
 * obtener las credenciales y el rol durante el login.
 * 2. `createUser`: Utilizado por el `UserService` del Gateway para registrar
 * un nuevo usuario en la base de datos centralizada de usuarios.
 */
package com.grupo13.microserviciogateway.feignClients;

import com.grupo13.microserviciogateway.feignModel.UserResponse;
import com.grupo13.microserviciogateway.service.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "usuario", url = "http://localhost:8082/api/usuarios")
public interface UsuarioFeign {

    @GetMapping("/username")
    UserResponse findUserByUsername(@RequestParam String username);

    @PostMapping
    UserResponse createUser(UserDTO user);
}
