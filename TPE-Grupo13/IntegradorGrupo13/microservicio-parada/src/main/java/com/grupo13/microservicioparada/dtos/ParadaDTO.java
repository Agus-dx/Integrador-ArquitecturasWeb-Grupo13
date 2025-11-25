/**
 * 📤 DTO Principal de Respuesta (Data Transfer Object) para la entidad Parada.
 *
 * Esta clase se utiliza para estructurar la información de una Parada
 * que se envía como respuesta a las peticiones HTTP. Su propósito es:
 * 1. Exponer la información geográfica y de identificación de la Parada.
 * 2. **Integración Inter-Servicio:** Incluye el campo 'monopatinesLibres'
 * (que usa el modelo Monopatin obtenido a través de Feign Client). Esto
 * demuestra que el Microservicio de Paradas enriquece sus respuestas con
 * datos de inventario del Microservicio de Monopatines.
 * 3. Facilitar la conversión desde la entidad JPA (a través del constructor ParadaDTO(Parada)).
 */
package com.grupo13.microservicioparada.dtos;

import com.grupo13.microservicioparada.feignModel.Monopatin;
import com.grupo13.microservicioparada.model.Parada;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ParadaDTO {
    private Long id;
    private String nombre;
    private String ciudad;
    private String direccion;
    private Double latitud;
    private Double longitud;
    private List<Monopatin> monopatinesLibres;

    public ParadaDTO(Parada parada) {
        this.id = parada.getId();
        this.nombre = parada.getNombre();
        this.ciudad = parada.getCiudad();
        this.direccion = parada.getDireccion();
        this.latitud = parada.getLatitud();
        this.longitud = parada.getLongitud();
    }
}
