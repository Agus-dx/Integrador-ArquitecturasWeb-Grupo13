/**
 * 💾 Repositorio de Spring Data MongoDB para la entidad Monopatin.
 *
 * Extiende MongoRepository, utilizando 'String' para el tipo de ID (típico en MongoDB).
 * Define métodos de consulta especializados, esenciales para las operaciones del servicio:
 * 1. findByIdParada: Búsqueda basada en el ID de la Parada (clave externa lógica).
 * 2. findByEstadoStringAndIdParada: Consulta nativa de MongoDB (@Query) para
 * filtrar monopatines por su estado Y la Parada a la que pertenecen (ej. Monopatines
 * 'LIBRE' en la Parada X).
 * 3. getReportesMantenimiento: Consulta nativa de MongoDB (@Query) para encontrar
 * monopatines cuyo campo 'kmRecorridos' sea mayor o igual ($gte) al kilometraje
 * máximo de mantenimiento, crucial para la logística.
 */
package com.grupo13.microserviciomonopatin.repository;

import com.grupo13.microserviciomonopatin.model.Monopatin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MonopatinRepository")
public interface MonopatinRepository extends MongoRepository<Monopatin, String> {

    /**
     * Busca todos los monopatines que se encuentren en una parada específica.
     *
     * Equivalente a la query Mongo:
     * { "idParada": idParada }
     */
    List<Monopatin> findByIdParada(Long idParada);

    /**
     * Busca monopatines filtrando por:
     *   - estado: un string que indica el estado del monopatín (ej: "DISPONIBLE", "MANTENIMIENTO")
     *   - idParada: identificador de la parada donde está ubicado
     *
     * Query Mongo manual:
     * { "estado": estado, "idParada": idParada }
     *
     * El uso de ?0 y ?1 hace referencia a los parámetros en orden.
     */
    @Query("{ 'estado' : ?0, 'idParada': ?1 }")
    List<Monopatin> findByEstadoStringAndIdParada(String estado, Long idParada);

    /**
     * Obtiene todos los monopatines cuyo kilometraje acumulado
     * sea MAYOR O IGUAL al valor indicado.
     *
     * Query Mongo:
     * {
     *    "kmRecorridos": { $gte: kmRecorridos }
     * }
     *
     * Esto suele usarse para generar reportes de mantenimiento
     * cuando superan cierto umbral de kilómetros.
     */
    @Query("{ 'kmRecorridos' : { $gte: ?0 } }")
    List<Monopatin> getReportesMantenimiento(Integer kmRecorridos);

    // Función Haversine para calcular la distancia en KM
    // Nota: Esta fórmula es compleja en JPQL/HQL puro. Si la BD lo soporta (MySQL con funciones trigonométricas),
    // se puede usar una @Query Nativa. Aquí se usa una versión compatible con muchas BDs.
    /*
     * Consigna G: Búsqueda de monopatines libres cercanos a una ubicación (Haversine).
     * Nota: Se utiliza la fórmula de Parada, pero se añade el filtro de estado 'LIBRE'.
     */
    @Query(value =
            "SELECT * FROM monopatin m WHERE m.estado = 'LIBRE' AND " + // Filtro de estado
                    "( 6371 * acos(" +
                    " LEAST(1.0, GREATEST(-1.0, cos(radians(:latitudUsuario)) *" +
                    " cos(radians(m.latitud)) * cos(radians(m.longitud) - radians(:longitudUsuario)) + " +
                    " sin(radians(:latitudUsuario)) * sin(radians(m.latitud)))))) < :radio " +
                    "ORDER BY ( 6371 * acos( LEAST(1.0, GREATEST(-1.0, cos(radians(:latitudUsuario)) *" +
                    " cos(radians(m.latitud)) * cos(radians(m.longitud) - radians(:longitudUsuario)) + " +
                    " sin(radians(:latitudUsuario)) * sin(radians(m.latitud))))))",
            nativeQuery = true)
    List<Monopatin> findMonopatinesCercanos(Double latitudUsuario, Double longitudUsuario,
                                            Double radio);
}
