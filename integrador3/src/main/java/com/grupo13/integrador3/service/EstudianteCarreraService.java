package com.grupo13.integrador3.service;

import com.grupo13.integrador3.dto.ReporteDTO;
import com.grupo13.integrador3.model.Carrera;
import com.grupo13.integrador3.model.Estudiante;
import com.grupo13.integrador3.model.EstudianteCarrera;
import com.grupo13.integrador3.model.EstudianteCarreraPK;
import com.grupo13.integrador3.repository.EstudianteCarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EstudianteCarreraService {

    @Autowired
    private EstudianteCarreraRepository estudianteCarreraRepository;
    @Autowired
    private CarreraService carreraService;
    @Autowired
    private EstudianteService estudianteService;

    @Transactional
    public void matricularEstudiante(Long estudianteId, Long carreraId) {
        Estudiante estudiante = estudianteService.findByIdEntity(estudianteId);
        Carrera carrera = carreraService.findByIdEntity(carreraId);
        //creamos la clave compuesta de la entidad-relacion
        EstudianteCarreraPK pk = new EstudianteCarreraPK(carrera.getId(), estudiante.getDni());
        //si ya existe no la agregamos de vuelta
        if (estudianteCarreraRepository.existsById(pk)) {
            throw new IllegalArgumentException("El estudiante ya está matriculado en esta carrera");
        }

        // 1. Obtener la fecha/año actual
        int anioActual = LocalDate.now().getYear();


        EstudianteCarrera nuevaMatricula = new EstudianteCarrera();
        nuevaMatricula.setId(pk); // pk compuesta por estudiante y carrera
        nuevaMatricula.setEstudiante(estudiante);
        nuevaMatricula.setCarrera(carrera);
        nuevaMatricula.setInscripcion(anioActual);
        nuevaMatricula.setGraduacion(0);

        estudianteCarreraRepository.save(nuevaMatricula);
    }

    @Transactional(readOnly = true)
    public List<ReporteDTO> getReportes() {
        return estudianteCarreraRepository.getReportes();
    }
}
