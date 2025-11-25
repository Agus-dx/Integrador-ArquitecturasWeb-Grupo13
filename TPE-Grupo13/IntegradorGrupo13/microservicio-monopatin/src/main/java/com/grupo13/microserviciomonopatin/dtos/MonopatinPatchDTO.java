/**
 * 📝 DTO de Entrada para Actualizaciones Parciales (Patch).
 *
 * Esta clase, definida como un 'record' inmutable de Java, se utiliza
 * para recibir los datos de una solicitud **PATCH** de un Monopatín.
 * * Uso del Record:
 * 1. Sus campos son públicos y finales por defecto, y actúan como DTOs inmutables.
 * 2. Es ideal para PATCH porque todos los campos son opcionales (pueden ser nulos).
 * La capa de Servicio verifica qué campos del DTO no son nulos y aplica solo
 * esos cambios a la entidad Monopatin existente.
 * 3. Los campos reflejan los atributos que cambian con mayor frecuencia en
 * tiempo real (ubicación, estado, métricas, parada).
 */
package com.grupo13.microserviciomonopatin.dtos;

import com.grupo13.microserviciomonopatin.model.Estado;


// Los record por default sus atributos son public y final y tienen getters y setters
// Por eso no se necesitan los getters y setters y se puede usar solo el nombre del atributo
// Ademas pueden tener cosas null y eso sirve para los patch
public record MonopatinPatchDTO(
        Estado estado,
        Double latitud,
        Double longitud,
        Integer kmRecorridos,
        Integer tiempoUsado,
        Long idParada)
{}
