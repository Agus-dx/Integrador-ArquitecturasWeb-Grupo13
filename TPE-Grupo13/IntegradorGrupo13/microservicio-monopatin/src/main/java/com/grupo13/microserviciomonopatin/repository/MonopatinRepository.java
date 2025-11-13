package com.grupo13.microserviciomonopatin.repository;

import com.grupo13.microserviciomonopatin.model.Monopatin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MonopatinRepository")
public interface MonopatinRepository extends MongoRepository<Monopatin, String> {

    List<Monopatin> findByIdParada(Long idParada);

    @Query("{ 'estado' : ?0, 'idParada': ?1 }")
    List<Monopatin> findByEstadoStringAndIdParada(String estado, Long idParada);

    @Query("{ 'kmRecorridos' : { $gte: ?0 } }")
    List<Monopatin> getReportesMantenimiento(Integer kmRecorridos);
}
