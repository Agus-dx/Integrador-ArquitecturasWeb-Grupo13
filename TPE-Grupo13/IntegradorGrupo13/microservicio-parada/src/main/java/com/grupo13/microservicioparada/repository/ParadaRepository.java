package com.grupo13.microservicioparada.repository;

import com.grupo13.microservicioparada.model.Parada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParadaRepository extends JpaRepository<Parada, Long> {

    /*fórmula del Haversine*/
    @Query(value =
            "SELECT * FROM parada WHERE ( 6371 * acos(" +
            " LEAST(1.0, GREATEST(-1.0, cos(radians(:latitudUsuario)) *" +
            " cos(radians(latitud)) * cos(radians(longitud) - radians(:longitudUsuario)) + " +
            " sin(radians(:latitudUsuario)) * sin(radians(latitud)))))) < :radio " +
            "ORDER BY ( 6371 * acos( LEAST(1.0, GREATEST(-1.0, cos(radians(:latitudUsuario)) *" +
            " cos(radians(latitud)) * cos(radians(longitud) - radians(:longitudUsuario)) + " +
            " sin(radians(:latitudUsuario)) * sin(radians(latitud))))))",
            nativeQuery = true)
    List<Parada> findParadasCercanas(Double latitudUsuario, Double longitudUsuario,
                                     Double radio);
}
