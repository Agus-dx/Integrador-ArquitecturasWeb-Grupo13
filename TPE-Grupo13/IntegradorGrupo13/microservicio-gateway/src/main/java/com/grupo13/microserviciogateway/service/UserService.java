/**
 * 🧑‍💻 Servicio de Usuarios (UserService) en el Gateway.
 *
 * Lógica de negocio para el registro de nuevos usuarios.
 * * Flujo:
 * 1. Hashing: Utiliza el `PasswordEncoder` (BCrypt) para hashear la contraseña
 * clara recibida en la solicitud.
 * 2. Delegación de Persistencia: Llama a `usuarioFeign.createUser` para que el
 * Microservicio de Usuarios/Cuentas se encargue de guardar el nuevo registro
 * en la base de datos.
 */
package com.grupo13.microserviciogateway.service;

import com.grupo13.microserviciogateway.feignClients.UsuarioFeign;
import com.grupo13.microserviciogateway.feignModel.UserResponse;
import com.grupo13.microserviciogateway.service.dto.user.UserDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioFeign usuarioFeign;

    public long saveUser(UserDTO request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        UserResponse userGuardado = usuarioFeign.createUser(request);
        return userGuardado.getId();
    }
}
