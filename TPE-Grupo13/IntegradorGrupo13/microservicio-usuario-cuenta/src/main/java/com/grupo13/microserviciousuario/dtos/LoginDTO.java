package com.grupo13.microserviciousuario.dtos;

import com.grupo13.microserviciousuario.entity.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginDTO {
    private Long id;
    private String username;
    private String password;
    private Rol rol;
}
