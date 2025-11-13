package com.grupo13.microserviciotarifa.repository;

import com.grupo13.microserviciotarifa.entity.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    @Modifying
    @Query("UPDATE Tarifa t SET t.monto = :monto WHERE t.fecha = :fecha")
    void actualizarTarifaDesdeFecha(@Param("monto") double monto, @Param("fecha") Date fecha);

    @Query("SELECT t FROM Tarifa t WHERE t.fecha <= :fecha ORDER BY t.fecha DESC")
    List<Tarifa> findTarifasVigentesDesde(@Param("fecha") Date fecha);

    Tarifa findByFecha(Date fecha);

    Tarifa findByMonto(Double monto);
}
