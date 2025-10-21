package com.grupo13.integrador3.service;

import com.grupo13.integrador3.dto.ReporteDTO;
import com.grupo13.integrador3.model.Carrera;
import com.grupo13.integrador3.model.Estudiante;
import com.grupo13.integrador3.model.EstudianteCarrera;
import com.grupo13.integrador3.repository.EstudianteCarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (estudiante != null && carrera != null) {
            EstudianteCarrera estudianteCarrera = new EstudianteCarrera();
            estudianteCarrera.setEstudiante(estudiante);
            estudianteCarrera.setCarrera(carrera);
            estudianteCarreraRepository.save(estudianteCarrera);
        }
        else {
            throw new IllegalArgumentException("Estudiante o Carrera no encontrados");
        }
    }

    @Transactional(readOnly = true)
    public List<ReporteDTO> getReportes() {
        return estudianteCarreraRepository.getReportes();
    }
}
