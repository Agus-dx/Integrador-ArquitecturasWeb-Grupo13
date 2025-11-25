/**
 * 🔗 Cliente Feign para el Microservicio de Tarifas.
 *
 * Esta interfaz define el contrato de comunicación HTTP síncrona con el
 * Microservicio de Tarifas, que es esencial para la lógica de cálculo de
 * precios en el Facturación Service.
 * * Métodos Clave:
 * 1. findTarifaById: Es el método CRÍTICO, utilizado por el Facturación Service
 * para obtener la información de la tarifa (monto variable, monto fijo, reglas
 * de pausa) asociada a un viaje específico. Sin estos datos, el cálculo de
 * importe es imposible.
 * 2. findAllTarifas: Permite obtener el listado completo de tarifas, útil
 * para propósitos de gestión o auditoría.
 * * Configuración:
 * Apunta al servicio en el puerto 8086, que corresponde al Microservicio de Tarifas.
 */
package com.grupo13.microserviciofacturacion.feignClient;

import com.grupo13.microserviciofacturacion.dto.TarifaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-tarifa", url = "http://localhost:8086/api/tarifas")
public interface TarifaFeignClient {

    @GetMapping("/")
    ResponseEntity<TarifaDTO[]> findAllTarifas();

    @GetMapping("/{id}")
    ResponseEntity<TarifaDTO> findTarifaById(@PathVariable Long id);
}
