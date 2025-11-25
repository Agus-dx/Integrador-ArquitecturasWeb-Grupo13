/**
 * 💾 Repositorio de Spring Data JPA para la entidad Viaje.
 *
 * Extiende JpaRepository para proporcionar métodos CRUD básicos y define
 * consultas JPQL personalizadas (`@Query`) para generar reportes complejos
 * sobre el uso de los viajes, como:
 * 1. Monopatines con más de 'X' viajes en un año específico.
 * 2. Reporte de actividad de usuarios (cantidad, km, tiempo) en un periodo de años.
 * 3. Reportes filtrados por cuentas asociadas.
 */
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
    SELECT new com.grupo13.microservicioviaje.dtos.ReporteViajePeriodoDTO(
        v.idMonopatin,      /* 1. String */
        CAST(COUNT(v) AS long), /* 2. Long (Forzado para coincidir con DTO) */
        :anioBuscado        /* 3. Integer */
    )
    FROM Viaje v
    WHERE FUNCTION('YEAR', v.fechaFin) = :anioBuscado 
               AND v.activo = false
    GROUP BY v.idMonopatin
    HAVING COUNT(v) >= :xViajes
    ORDER BY COUNT(v) DESC
   """)
    List<ReporteViajePeriodoDTO> getReporteViajeAnio(Integer anioBuscado,Long xViajes);


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
                    v.idUsuario IN (:usuariosRol)
            GROUP BY v.idUsuario
            ORDER BY COUNT(v) DESC 
            """)
    List<ReporteViajeUsuariosDTO> getReportesViajesPorUsuariosPeriodoPorTipoUsuario(Integer aniDesde, Integer anioHasta, Set<Long> usuariosRol);



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

    /**
     * Consigna H: Calcula el tiempo total de uso (en minutos) para un conjunto
     * de IDs de usuario/cuenta dentro de un rango de años.
     *
     * NOTA: Utiliza v.idUsuario, no v.idCuenta, ya que el Service ya resolvió los IDs de usuario.
     */
    @Query("""
    SELECT SUM(v.tiempoTotalMinutos) 
    FROM Viaje v
    WHERE v.activo = false AND 
          FUNCTION('YEAR', v.fechaFin) BETWEEN :anioDesde AND :anioHasta AND 
          v.idUsuario IN :usuarioIds  /* <--- CRÍTICO: Debe ser idUsuario IN (propios + asociados) */
    """)
    Long getDuracionTotalUsoByUsuarios(
            Set<Long> usuarioIds,
            Integer anioDesde,
            Integer anioHasta);
}
