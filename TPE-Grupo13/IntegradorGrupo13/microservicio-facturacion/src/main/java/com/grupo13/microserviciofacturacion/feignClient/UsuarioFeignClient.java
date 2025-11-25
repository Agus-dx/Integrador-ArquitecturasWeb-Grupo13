/**
 * 🔗 Cliente Feign para el Microservicio de Usuarios/Cuentas.
 *
 * Esta interfaz define el contrato de comunicación HTTP síncrona con el
 * Microservicio de Usuarios, lo cual es vital para la lógica de facturación Premium.
 * * Métodos Clave:
 * 1. getUsuarioPremium: Permite al Facturación Service consultar el estado
 * Premium del usuario (cupo mensual de KM, KM consumidos). Esto es necesario
 * para **calcular el importe** con el descuento correspondiente.
 * 2. actualizarKmConsumidos: Llama al servicio de Usuarios para **actualizar
 * el contador de KM consumidos** después de que se ha generado la factura de un
 * viaje Premium (para que el cupo restante sea correcto en futuros viajes).
 * * Configuración:
 * Apunta al servicio en el puerto 8081, que corresponde al Microservicio de Usuarios.
 */
package com.grupo13.microserviciofacturacion.feignClient;

import com.grupo13.microserviciofacturacion.dto.UsuarioPremiumDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "microservicio-usuario", url = "http://localhost:8081/api/usuarios")
public interface UsuarioFeignClient {

    @GetMapping("/{id}/premium")
    ResponseEntity<UsuarioPremiumDTO> getUsuarioPremium(@PathVariable Long id);

    @PutMapping("/{id}/consumir-km")
    ResponseEntity<Void> actualizarKmConsumidos(@PathVariable Long id, @RequestParam Double kmConsumidos);
}

