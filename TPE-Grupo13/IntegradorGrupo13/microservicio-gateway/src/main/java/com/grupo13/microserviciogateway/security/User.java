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
// ENTIDAD QUE SE USA PARA GENERAR LA AUTORIDAD USUARIO DE SPRING SECURITY
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
