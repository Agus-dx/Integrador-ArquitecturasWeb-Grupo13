/**
 * 📝 Controlador de Registro (UserController).
 *
 * Expone el endpoint POST /api/registrar, que permite a los nuevos usuarios
 * registrarse en la plataforma.
 * * Flujo:
 * 1. Recibe `UserDTO` con los datos del nuevo usuario.
 * 2. Delega la lógica de negocio al `UserService` (que gestiona el hashing
 * y la llamada al Microservicio de Usuarios).
 * 3. Devuelve el ID del nuevo usuario con el código HTTP 201 (Created).
 */
package com.grupo13.microserviciogateway.Controller;

import com.grupo13.microserviciogateway.service.UserService;
import com.grupo13.microserviciogateway.service.dto.user.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registrar")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<?> saveUser( @RequestBody @Valid UserDTO userDTO) {
        final var id = userService.saveUser( userDTO );
        return new ResponseEntity<>( id, HttpStatus.CREATED );
    }
}
