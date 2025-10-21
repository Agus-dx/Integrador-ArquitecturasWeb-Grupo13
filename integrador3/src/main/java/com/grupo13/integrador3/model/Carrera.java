package com.grupo13.integrador3.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrera")
@Getter
@Setter
@NoArgsConstructor
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;
    @Column(name = "duracion", nullable = false)
    private int duracion;
    @OneToMany(mappedBy = "carrera", fetch = FetchType.LAZY)
    private List<EstudianteCarrera> estudiantes;

    public Carrera(String nombre,int duracion){
        this.nombre = nombre;
        this.duracion = duracion;
        this.estudiantes = new ArrayList<>();
    }
}


