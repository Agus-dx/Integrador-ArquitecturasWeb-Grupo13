/**
 * 💾 Repositorio de Spring Data JPA para la entidad Tarifa.
 *
 * Extiende JpaRepository para proporcionar métodos CRUD básicos.
 * Define métodos de consulta basados en nombres (findByFecha, findByMonto)
 * y dos consultas JPQL personalizadas clave:
 * 1. findTarifasVigentesDesde: Busca todas las tarifas cuya fecha de inicio es
 * anterior o igual a la fecha dada, ordenadas descendentemente por fecha.
 * Esto permite identificar la tarifa más reciente y vigente para un momento dado.
 * 2. actualizarTarifaDesdeFecha: Define una consulta DML (@Modifying) para
 * actualizar el monto de una tarifa específica basada en su fecha, aunque esta
 * lógica se implementa de manera transaccional en el Service.
 */
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
