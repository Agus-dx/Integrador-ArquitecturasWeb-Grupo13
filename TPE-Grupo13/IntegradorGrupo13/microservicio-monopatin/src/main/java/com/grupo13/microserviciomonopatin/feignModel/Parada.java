/**
 * 📦 Modelo de Datos Externo (Feign Model/Client Model) para Parada.
 *
 * Esta clase NO es una entidad de MongoDB, sino un modelo de objeto utilizado
 * para deserializar la respuesta JSON recibida desde el **Microservicio de Paradas** * a través del Feign Client.
 * Su propósito es permitir al Microservicio de Monopatines manejar temporalmente
 * la información esencial de la Parada (ubicación y detalles) para poder
 * validar referencias (al guardar un monopatin) o enriquecer respuestas
 * (al obtener un monopatin con su parada asociada).
 */
package com.grupo13.microserviciomonopatin.feignModel;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Parada {
    private String nombre;
    private String ciudad;
    private String direccion;
    private Double latitud;
    private Double longitud;
}
