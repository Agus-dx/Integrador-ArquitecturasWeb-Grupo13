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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "inscripcion",nullable = false)
    private int inscripcion;
    @Column(name = "graduacion")
    private int graduacion;
    @Column(name = "antiguedad",nullable = false)
    private int antiguedad;
    @ManyToOne
    @JoinColumn(name = "id_estudiante", nullable = false)
    private Estudiante estudiante;
    @ManyToOne
    @JoinColumn(name = "id_carrera", nullable = false)
    private Carrera carrera;

    public EstudianteCarrera(int fechaIngreso, int fechaGraduacion, int antiguedad, Estudiante estudiante,
                             Carrera carrera) {
        this.inscripcion = fechaIngreso;
        this.graduacion = fechaGraduacion;
        this.antiguedad = antiguedad;
        this.estudiante = estudiante;
        this.carrera = carrera;
    }
}


