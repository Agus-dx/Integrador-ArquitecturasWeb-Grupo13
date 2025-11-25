/**
 * 🔗 Modelo Feign (o DTO de Comunicación) para la entidad Usuario.
 *
 * Esta clase sirve como una representación local de la entidad 'Usuario'
 * que reside en otro microservicio. Es utilizado por Feign Client para
 * deserializar las respuestas JSON obtenidas del Microservicio de Usuario,
 * permitiendo al Microservicio de Viajes acceder a la información básica del usuario.
 */
package com.grupo13.microservicioviaje.feignModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private Long id;
    private String nombre;
    private String apellido;
}
