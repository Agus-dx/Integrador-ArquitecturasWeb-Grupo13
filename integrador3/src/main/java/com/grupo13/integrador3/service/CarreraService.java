package com.grupo13.integrador3.service;

import com.grupo13.integrador3.dto.CarreraDTO;
import com.grupo13.integrador3.dto.EstudianteDTO;
import com.grupo13.integrador3.model.Carrera;
import com.grupo13.integrador3.model.Estudiante;
import com.grupo13.integrador3.repository.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarreraService {

    @Autowired
    private CarreraRepository carreraRepository;

    @Transactional(readOnly = true)
    public List<CarreraDTO> getCarrerasConEstudiantes() {
        return carreraRepository.getCarrerasConEstudiantes();
    }

    @Transactional(readOnly = true)
    public List<CarreraDTO> findAll() {
        return carreraRepository.findAll().stream().map(CarreraDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CarreraDTO findById(Long id) {
        try{
            return carreraRepository.findById(id).map(CarreraDTO::new).orElseThrow(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
//creo que es este metodo que deberia de retornar un DTO
    @Transactional(readOnly = true)
    public Carrera findByIdEntity(Long id){
        try{
            return carreraRepository.findById(id).orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public CarreraDTO addCarrera(Carrera carrera){
            Carrera carr = carreraRepository.save(carrera);
            CarreraDTO  carrDTO = new CarreraDTO(carr);
            return carrDTO;
    }
}
