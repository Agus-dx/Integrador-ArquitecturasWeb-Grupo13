package com.grupo13.microserviciogateway.feignModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// LO QUE ME DEVUELVE CADA PETICION EN EL FEIGN
public class UserResponse {
    private Long id;
    private String username;
    private String password;
    private String rol;
}
