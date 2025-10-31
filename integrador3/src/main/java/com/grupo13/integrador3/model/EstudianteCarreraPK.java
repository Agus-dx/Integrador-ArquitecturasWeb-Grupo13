package com.grupo13.integrador3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EstudianteCarreraPK {

    /**
     * Clave foránea que referencia el ID de la Carrera (Primary Key de Carrera)
     */
    @Column(name = "id_carrera")
    private Long idCarrera;

    /**
     * Clave foránea que referencia el DNI del Estudiante (Primary Key de Estudiante)
     */
    @Column(name = "id_estudiante")
    private Long idEstudiante;

}
