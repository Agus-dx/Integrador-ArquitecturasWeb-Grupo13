package com.grupo13.integrador3.repository;

import com.grupo13.integrador3.dto.ReporteDTO;
import com.grupo13.integrador3.model.EstudianteCarrera;
import com.grupo13.integrador3.model.EstudianteCarreraPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteCarreraRepository extends JpaRepository<EstudianteCarrera, Long> {

    @Query("SELECT new com.grupo13.integrador3.dto.ReporteDTO(" +
            "ec.graduacion, c.nombre, COUNT(ec), " +
            "(SELECT COUNT(ec2) FROM EstudianteCarrera ec2 " +
            "WHERE ec2.carrera.nombre = c.nombre)) " +
            "FROM EstudianteCarrera ec JOIN ec.carrera c " +
            "WHERE ec.graduacion != 0 " +
            "GROUP BY ec.graduacion, c.nombre " +
            "ORDER BY c.nombre, ec.graduacion")
    List<ReporteDTO> getReportes();

    boolean existsById(EstudianteCarreraPK pk);
}
