package com.grupo13.integrador3.repository;

import com.grupo13.integrador3.dto.CarreraDTO;
import com.grupo13.integrador3.model.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CarreraRepositorio")
public interface CarreraRepository extends JpaRepository<Carrera, Long> {

    @Query("SELECT new com.grupo13.integrador3.dto.CarreraDTO(c.nombre, c.duracion, COUNT(c)) " +
            "FROM Carrera c JOIN  c.estudiantes e " +
            "GROUP BY c.id, c.nombre " +
            "ORDER BY COUNT(c) DESC")
    List<CarreraDTO> getCarrerasConEstudiantes();
}

