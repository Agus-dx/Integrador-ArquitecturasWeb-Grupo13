/**
 * 💾 Repositorio de Spring Data JPA para la entidad Parada.
 *
 * Extiende JpaRepository para proporcionar métodos CRUD básicos.
 * Su función principal es implementar la lógica de **Geocercanía** * mediante una consulta SQL nativa (@NativeQuery) que utiliza la
 * **Fórmula del Haversine**.
 * - findParadasCercanas: Calcula la distancia esférica entre la ubicación
 * del usuario y todas las paradas, devolviendo solo aquellas que están
 * dentro de un radio específico (en kilómetros).
 */
package com.grupo13.microservicioparada.repository;

import com.grupo13.microservicioparada.model.Parada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
