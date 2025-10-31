package com.grupo13.integrador3.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estudiantecarrera")
@Getter
@Setter
@NoArgsConstructor
public class EstudianteCarrera {
    /**
     * Clave primaria compuesta de la entidad
     */
    @EmbeddedId
    private EstudianteCarreraPK id;

    /**
     * Entidad Carrera referenciada
     * * @MapsId("idCarrera"): Mapea el campo 'idCarrera' de la clave compuesta (EstudianteCarreraPK)
     */
    @ManyToOne (fetch=FetchType.LAZY)
    @MapsId("idCarrera")
    @JoinColumn(name = "id_carrera", nullable = false, insertable = false, updatable = false)
    private Carrera carrera;

    /**
     * Entidad Estudiante referenciada
     * * @MapsId("idEstudiante"): Mapea el campo 'idEstudiante' de la clave compuesta (EstudianteCarreraPK)
     * a la columna 'id_estudiante'
     */
    @ManyToOne (fetch=FetchType.LAZY)
    @MapsId("idEstudiante")
    @JoinColumn(name = "id_estudiante", nullable = false, insertable = false, updatable = false)
    private Estudiante estudiante;

    private int inscripcion;
    @Column(name = "graduacion")
    private int graduacion;
    @Column(name = "antiguedad",nullable = false)
    private int antiguedad;


    public EstudianteCarrera(int fechaIngreso, int fechaGraduacion, int antiguedad, Estudiante estudiante,
                             Carrera carrera) {
        this.inscripcion = fechaIngreso;
        this.graduacion = fechaGraduacion;
        this.antiguedad = antiguedad;
        this.estudiante = estudiante;
        this.carrera = carrera;

        this.id = new EstudianteCarreraPK(carrera.getId(), estudiante.getDni());
    }
}


