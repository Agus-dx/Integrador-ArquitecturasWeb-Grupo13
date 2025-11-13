package com.grupo13.microservicioadmin.services;

import com.grupo13.microservicioadmin.feignClients.UsuarioFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioFeignClient usuarioFeignClient;

    
    public String getUsuario() {
        return usuarioFeignClient.holaMundo();
    }
}
