package com.grupo13.microservicioviaje.repository;

import com.grupo13.microservicioviaje.dtos.ReporteViajePeriodoDTO;
import com.grupo13.microservicioviaje.dtos.ReporteViajeUsuariosDTO;
import com.grupo13.microservicioviaje.model.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository("ViajeRepository")
public interface ViajeRepository extends JpaRepository<Viaje, Long> {

    @Query("""
            SELECT new com.grupo13.microservicioviaje.dtos.ReporteViajePeriodoDTO(v.idMonopatin,COUNT(v),:anio)
            FROM Viaje v
            WHERE FUNCTION('YEAR', v.fechaFin) = :anio 
            GROUP BY v.idMonopatin
            HAVING COUNT(v) > :xViajes
            ORDER BY COUNT(v) DESC
            """)
    List<ReporteViajePeriodoDTO> getReporteViajeAnio(Integer anio,Integer xViajes);

    @Query("""
            SELECT new com.grupo13.microservicioviaje.dtos.ReporteViajeUsuariosDTO(v.idUsuario,COUNT(v),SUM(v.kilometrosRecorridos),SUM(v.tiempoTotalMinutos))
            FROM Viaje v 
            WHERE v.activo = false AND 
                    FUNCTION('YEAR', v.fechaFin) BETWEEN :aniDesde AND :anioHasta
            GROUP BY v.idUsuario
            ORDER BY COUNT(v) DESC 
            """)
    List<ReporteViajeUsuariosDTO> getReportesViajesPorUsuariosPeriodo(Integer aniDesde, Integer anioHasta);

    @Query("""
            SELECT new com.grupo13.microservicioviaje.dtos.ReporteViajeUsuariosDTO(v.idUsuario,COUNT(v),SUM(v.kilometrosRecorridos),SUM(v.tiempoTotalMinutos))
            FROM Viaje v
            WHERE v.activo = false AND 
                    FUNCTION('YEAR', v.fechaFin) BETWEEN :aniDesde AND :anioHasta AND 
                    v.idCuenta IN :cuentaIds
            GROUP BY v.idUsuario
            """)
    List<ReporteViajeUsuariosDTO> getReportesViajesPorUsuarioYcuentasAsociadasPeriodo(
            Set<Long> cuentaIds, Integer aniDesde, Integer anioHasta);
}
