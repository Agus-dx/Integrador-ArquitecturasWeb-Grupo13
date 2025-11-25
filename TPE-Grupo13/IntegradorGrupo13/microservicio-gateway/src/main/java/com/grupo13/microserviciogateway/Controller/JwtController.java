/**
 * 🔑 Controlador JWT (JwtController).
 *
 * Expone el endpoint POST /api/token, responsable de recibir las credenciales
 * (username/password) y generar un JWT si la autenticación es exitosa.
 * * Flujo:
 * 1. Recibe `LoginDTO`.
 * 2. Autentica las credenciales usando `AuthenticationManagerBuilder` (que a su
 * vez usa `DomainUserDetailsService` para ir al Microservicio de Usuarios).
 * 3. Si es exitoso, llama a `tokenProvider.createToken(authentication)`.
 * 4. Devuelve el JWT en el cuerpo de la respuesta y en el encabezado `Authorization`.
 */
package com.grupo13.microserviciogateway.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.grupo13.microserviciogateway.security.jwt.JwtFilter;
import com.grupo13.microserviciogateway.security.jwt.TokenProvider;
import com.grupo13.microserviciogateway.service.dto.login.LoginDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class JwtController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping()
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginDTO request) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final var jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
