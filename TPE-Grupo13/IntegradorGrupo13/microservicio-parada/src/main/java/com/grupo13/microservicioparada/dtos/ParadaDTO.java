package com.grupo13.microservicioparada.dtos;

import com.grupo13.microservicioparada.model.Parada;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ParadaDTO {
    private String nombre;
    private String ciudad;
    private String direccion;
    private Double latitud;
    private Double longitud;

    public ParadaDTO(Parada parada) {
        this.nombre = parada.getNombre();
        this.ciudad = parada.getCiudad();
        this.direccion = parada.getDireccion();
        this.latitud = parada.getLatitud();
        this.longitud = parada.getLongitud();
    }
}
