package com.grupo13.microservicioviaje.feignModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Parada {
    private String nombre;
    private String ciudad;
    private String direccion;
    private Double latitud;
    private Double longitud;
}
