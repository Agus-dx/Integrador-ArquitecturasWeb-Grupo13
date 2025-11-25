/**
 * 💾 Repositorio JPA para la entidad Factura.
 *
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas.
 * Define consultas personalizadas usando JPQL (Java Persistence Query Language)
 * para las operaciones de reporting y búsqueda basadas en fechas.
 * * Consultas Clave:
 * 1. findByFechaBetween: Permite consultar todas las facturas emitidas en un
 * rango de fechas específico.
 * 2. getTotalFacturadoPorRangoMeses: Consulta crítica que realiza una **agregación** * (SUM) sobre el importe de las facturas dentro de un rango de meses y un año
 * dados. Utiliza funciones SQL nativas como YEAR() y MONTH() dentro del JPQL.
 */
package com.grupo13.microserviciofacturacion.repository;

import com.grupo13.microserviciofacturacion.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("FacturaRepository")
public interface FacturaRepository extends JpaRepository<Factura,Long> {

    @Query("SELECT f FROM Factura f WHERE f.fechaEmision BETWEEN :desde AND :hasta")
    List<Factura> findByFechaBetween(@Param("desde") Date fecha1, @Param("hasta") Date fecha2);

    @Query("SELECT COALESCE(SUM(f.importe), 0.0) FROM Factura f " +
            "WHERE YEAR(f.fechaEmision) = :anio " +
            "AND MONTH(f.fechaEmision) BETWEEN :mesDesde AND :mesHasta")
    Double getTotalFacturadoPorRangoMeses(@Param("anio") int anio, @Param("mesDesde") int mesDesde, @Param("mesHasta") int mesHasta);

}
