package com.grupo13.integrador3.service;

import com.grupo13.integrador3.dto.EstudianteDTO;
import com.grupo13.integrador3.model.Estudiante;
import com.grupo13.integrador3.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;



@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Transactional(readOnly = true)
    public List<EstudianteDTO> findAll() {
        return estudianteRepository.findAll().stream().map(EstudianteDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public EstudianteDTO findById(Long id) {
        return estudianteRepository.findById(id).map(EstudianteDTO::new)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }

    @Transactional(readOnly = true)
    public Estudiante findByIdEntity(Long id){
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }



    @Transactional(readOnly = true)
    public List<EstudianteDTO> getEstudiantesOrderBy(String criterio) {
        criterio = criterio.toLowerCase();

        List<String> camposOrdenados = List.of("dni", "nombre","apellido", "numero_libreta", "genero", "edad", "ciudad");
        if(!camposOrdenados.contains(criterio)){
            return new ArrayList<>();
        }

        return estudianteRepository.findAll(Sort.by(criterio))
                .stream().map(EstudianteDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public EstudianteDTO findEstudianteByNumeroLibreta(Long nroLibreta) {
        try{
            return estudianteRepository.getEstudianteByLU(nroLibreta);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public List<EstudianteDTO> findEstudiantesByGenero(String genero) {
        return estudianteRepository.getEstudiantesByGenero(genero);
    }

    @Transactional(readOnly = true)
    public List<EstudianteDTO> findEstudiantesByCarreraAndCiudadOrigen(String carrera,
            String ciudadOrigen) {
        return estudianteRepository.getEstudiantesByCarreraAndCiudad(carrera, ciudadOrigen);
    }

    @Transactional
    public EstudianteDTO addEstudiante(Estudiante estudiante) throws Exception {
        try{
            Estudiante est = estudianteRepository.save(estudiante);
            return new EstudianteDTO(est);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public void deleteById(Long id) {
        estudianteRepository.deleteById(id);
    }
}
