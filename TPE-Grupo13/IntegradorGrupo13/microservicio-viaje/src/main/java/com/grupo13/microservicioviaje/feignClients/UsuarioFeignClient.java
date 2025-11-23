package com.grupo13.microservicioviaje.feignClients;

import com.grupo13.microservicioviaje.feignModels.Cuenta;
import com.grupo13.microservicioviaje.feignModels.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(name = "microservicio-usuarios", url = "http://localhost:8082/api/usuarios")
public interface UsuarioFeignClient {

    @GetMapping("/{id}")
    Usuario findById(@PathVariable Long id);

    @GetMapping("/{idUsuario}/cuenta-asociada/{idCuenta}")
    Boolean cuentaAsociada(@PathVariable Long idUsuario, @PathVariable Long idCuenta);

    @GetMapping("/cuentas/{id}")
    Set<Cuenta> getCuentasByUsuario(@PathVariable Long id);

    @GetMapping("tipo/{rol}")
    Set<Long> getUsuarioByRol(@PathVariable String rol);
}
