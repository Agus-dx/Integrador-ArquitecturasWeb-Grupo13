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
