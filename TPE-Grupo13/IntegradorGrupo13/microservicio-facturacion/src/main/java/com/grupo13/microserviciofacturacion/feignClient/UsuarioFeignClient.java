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

