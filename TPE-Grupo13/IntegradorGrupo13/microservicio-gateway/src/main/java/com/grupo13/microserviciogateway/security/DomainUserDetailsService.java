/**
 * 🧑‍💻 Servicio de Detalles de Usuario (DomainUserDetailsService).
 *
 * Clase crítica que implementa `UserDetailsService` de Spring Security.
 * Es la responsable de obtener los detalles del usuario durante el proceso de login.
 * * Flujo de Delegación:
 * 1. Recibe el `username` durante el intento de login.
 * 2. Llama al `UsuarioFeign` para consultar al Microservicio de Usuarios/Cuentas.
 * 3. Recibe `UserResponse` (que incluye el hash de la contraseña y el rol).
 * 4. Crea el objeto `User` interno (con sus Authorities).
 * 5. Devuelve el `UserDetails` que Spring Security utiliza para comparar el
 * hash de la contraseña (usando el `PasswordEncoder` configurado).
 */
package com.grupo13.microserviciogateway.security;

import com.grupo13.microserviciogateway.feignClients.UsuarioFeign;
import com.grupo13.microserviciogateway.feignModel.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UsuarioFeign usuarioFeign;

    public DomainUserDetailsService( UsuarioFeign usuarioFeign ) {
        this.usuarioFeign = usuarioFeign;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username ) {
        log.debug("Authenticating {}", username);

        UserResponse userFeign = usuarioFeign.findUserByUsername(username);// Traigo usuario desde el servicio de usuarios
        User userConRoles = new User(userFeign);// Creo usuario con roles,ya que en un futuro puede tener varios  roles

        return this.createSpringSecurityUser( userConRoles );// Devuelvo el userDetails con los authorities
    }

    private UserDetails createSpringSecurityUser( User user ) {
        List<GrantedAuthority> grantedAuthorities = user
                .getAuthorities()
                .stream()
                .map( SimpleGrantedAuthority::new )
                .collect( Collectors.toList() );

        return new org.springframework.security.core.userdetails.User( user.getUsername(), user.getPassword(), grantedAuthorities );
    }

}
