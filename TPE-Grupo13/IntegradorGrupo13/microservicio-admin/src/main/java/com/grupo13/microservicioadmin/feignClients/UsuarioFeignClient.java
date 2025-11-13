package com.grupo13.microservicioadmin.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="microservicio-usuario", url="http://localhost:8087/usuario")
public interface UsuarioFeignClient {

    // EN PROCESO
}
