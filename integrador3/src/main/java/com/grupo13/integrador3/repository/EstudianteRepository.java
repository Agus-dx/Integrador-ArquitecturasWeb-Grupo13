package com.grupo13.integrador3.repository;

import com.grupo13.integrador3.dto.EstudianteDTO;
import com.grupo13.integrador3.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("EstudianteRepositorio")
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    @Query("SELECT new com.grupo13.integrador3.dto.EstudianteDTO(" +
            "e.dni, e.nombre, e.apellido, e.edad, e.genero, " +
            "e.ciudadOrigen, e.numeroLibreta) " +
            "FROM Estudiante e WHERE e.numeroLibreta = :numeroLibreta")
    EstudianteDTO getEstudianteByLU(Long nroLibreta);

    @Query("SELECT new com.grupo13.integrador3.dto.EstudianteDTO(" +
            "e.dni,e.nombre,e.apellido,e.edad,e.genero," +
            "e.ciudadOrigen,e.numeroLibreta) " +
            "FROM Estudiante e " +
            "WHERE LOWER(e.genero) = LOWER(:genero)")
    List<EstudianteDTO> getEstudiantesByGenero(String genero);

    @Query("SELECT new com.grupo13.integrador3.dto.EstudianteDTO(" +
            "e.dni,e.nombre,e.apellido,e.edad,e.genero," +
            "e.ciudadOrigen,e.numeroLibreta) " +
            "FROM Estudiante e JOIN e.listCarreras m " +
            "JOIN m.carrera c " +
            "WHERE LOWER(c.nombre) = LOWER(:carrera) " +
            "AND LOWER(e.ciudadOrigen) = LOWER(:ciudadOrigen)")
    List<EstudianteDTO> getEstudiantesByCarreraAndCiudad(String carrera, String ciudadOrigen);
}
