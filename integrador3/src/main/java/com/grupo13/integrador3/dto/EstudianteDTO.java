package com.grupo13.integrador3.dto;

import com.grupo13.integrador3.model.Estudiante;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EstudianteDTO {
    private long dni;
    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private String ciudadOrigen;
    private long numeroLibreta;

    public EstudianteDTO(Estudiante estudiante) {
        this.dni = estudiante.getDni();
        this.nombre = estudiante.getNombre();
        this.apellido = estudiante.getApellido();
        this.edad = estudiante.getEdad();
        this.genero = estudiante.getGenero();
        this.ciudadOrigen = estudiante.getCiudadOrigen();
        this.numeroLibreta = estudiante.getNumeroLibreta();
    }
}
