/**
 * 📞 Cliente Feign para el Microservicio de Usuarios.
 *
 * Esta interfaz declara los contratos de comunicación (endpoints) con el
 * Microservicio de Usuarios (microservicio-usuarios), permitiendo al
 * Microservicio de Viajes realizar llamadas HTTP sencillas y declarativas.
 * Es vital para:
 * 1. Validar la existencia de un usuario.
 * 2. Verificar la asociación de una cuenta con un usuario (lógica de negocio).
 * 3. Obtener listas de cuentas y usuarios filtrados por rol para reportes complejos.
 */
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

    @GetMapping("/cuentas/{idCuenta}/usuarios")
    Set<Usuario> getUsuariosByCuenta(@PathVariable("idCuenta") Long idCuenta);

}
