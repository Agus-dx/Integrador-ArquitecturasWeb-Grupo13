package com.grupo13.integrador3.dto;

import com.grupo13.integrador3.model.Carrera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarreraDTO {
    private String nombre;
    private int duracion;
    private long cantEstudiantes;

    public CarreraDTO(Carrera carrera) {
        this.nombre = carrera.getNombre();
        this.duracion = carrera.getDuracion();
        this.cantEstudiantes = carrera.getEstudiantes().size();
    }
}

