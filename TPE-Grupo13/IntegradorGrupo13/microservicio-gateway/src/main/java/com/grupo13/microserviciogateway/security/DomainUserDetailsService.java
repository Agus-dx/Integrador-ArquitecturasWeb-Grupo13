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
