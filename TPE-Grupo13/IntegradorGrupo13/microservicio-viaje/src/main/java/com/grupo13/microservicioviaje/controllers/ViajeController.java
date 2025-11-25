/**
 * 🌐 Controlador REST (API Layer) para la gestión de Viajes.
 *
 * Es el punto de entrada HTTP para el Microservicio de Viajes.
 * Se encarga de mapear las URLs (GET, POST, PATCH, DELETE) a los métodos del
 * servicio, procesar los parámetros de la petición (Path Variables, Request Body,
 * Query Params) y construir la respuesta HTTP adecuada, incluyendo la gestión
 * de códigos de estado como 200, 201, 204 y el manejo de excepciones de negocio
 * (400 Bad Request) y no encontradas (404 Not Found).
 */
package com.grupo13.microservicioviaje.controllers;

import com.grupo13.microservicioviaje.dtos.ReporteViajePeriodoDTO;
import com.grupo13.microservicioviaje.dtos.ReporteViajeUsuariosDTO;
import com.grupo13.microservicioviaje.dtos.ViajeDTO;
import com.grupo13.microservicioviaje.model.Viaje;
import com.grupo13.microservicioviaje.services.ViajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/viajes")
public class ViajeController {
    private final ViajeService service;

    /**
     * Obtiene todos los viajes. Retorna 204 (No Content) si la lista está vacía.
     */
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<ViajeDTO> viajes = service.findAll();

        if(viajes.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(viajes);
    }

    /**
     * Busca un viaje por ID. Retorna 404 (Not Found) si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ViajeDTO> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Genera reportes de viajes basados en diferentes combinaciones de parámetros.
     * Manejo general de errores internos (500).
     */
    @GetMapping("/reportes")
    public ResponseEntity<?> getReporteViajes(
            @RequestParam(required = false, name = "anio") Integer anio,
            @RequestParam(required = false, name = "cantidad") Long cantidad,
            @RequestParam(required = false, name = "anioDesde") Integer anioDesde,
            @RequestParam(required = false, name = "anioHasta") Integer anioHasta,
            @RequestParam(required = false, name = "rol") String rol) {

        try {
            // c - Reporte por año específico
            if (anio != null && cantidad != null) {
                List<ReporteViajePeriodoDTO> reportes = service.getReporteViajeAnio(anio, cantidad);
                return reportes.isEmpty()
                        ? ResponseEntity.noContent().build()
                        : ResponseEntity.ok(reportes);
            }

            // Reporte general por período
            if (anioDesde != null && anioHasta != null && rol != null) {
                List<ReporteViajeUsuariosDTO> reportes = service.getReporteViajesPorUsuariosPeriodo(anioDesde, anioHasta, rol);
                return reportes.isEmpty()
                        ? ResponseEntity.noContent().build()
                        : ResponseEntity.ok(reportes);
            }
            return ResponseEntity.badRequest()
                    .body("Parámetros inválidos.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar reporte: " + e.getMessage());
        }
    }

    /**
     * Reporte C: Consulta monopatines con más de X viajes en un año específico.
     * Requiere: anio y cantidad (X viajes).
     */
    @GetMapping("/reportes/monopatines-mas-x-viajes-anio")
    public ResponseEntity<?> getMonopatinesMasXViajesAnio(
            @RequestParam(name = "anio", required = true) Integer anio,
            @RequestParam(name = "cantidad", required = true) Long cantidad) {
        try {
            List<ReporteViajePeriodoDTO> reportes = service.getReporteViajeAnio(anio, cantidad);

            return reportes.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(reportes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar reporte de monopatines populares: " + e.getMessage());
        }
    }

    /**
     * Reporte E: Consulta usuarios que más utilizan monopatines en un periodo, filtrado por rol.
     * Requiere: anioDesde, anioHasta y rol.
     */
    @GetMapping("/reportes/usuarios-top-periodo")
    public ResponseEntity<?> getUsuariosTopPeriodo(
            @RequestParam(name = "anioDesde", required = true) Integer anioDesde,
            @RequestParam(name = "anioHasta", required = true) Integer anioHasta,
            @RequestParam(name = "rol", required = true) String rol) {
        try {
            List<ReporteViajeUsuariosDTO> reportes = service.getReporteViajesPorUsuariosPeriodo(anioDesde, anioHasta, rol);

            return reportes.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(reportes);

        } catch (Exception e) {
            e.printStackTrace();
            // La excepción de tipo de rol inválido se manejará aquí, devolviendo 500 o puedes refinarla a 400.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar reporte de usuarios top: " + e.getMessage());
        }
    }


    @GetMapping("/reportes-usuario")
    public ResponseEntity<?> getReporteViajesUsuario(
            @RequestParam(required = true, name = "anioDesde") Integer anioDesde,
            @RequestParam(required = true, name = "anioHasta") Integer anioHasta,
            @RequestParam(required = true, name = "idUsuario") Long idUsuario,
            @RequestParam(required = false, defaultValue = "false", name = "incluirAsociados")
            Boolean incluirAsociados) {

        try {
            // Reporte de usuario en período (años) y cuentas asociadas
            // El servicio debe buscar viajes cuya fecha de finalización esté en el rango [anioDesde, anioHasta].
            Map<String, Object> reportes = service.getReportesUsuarioYasociadosPerido(
                    idUsuario,
                    anioDesde,
                    anioHasta,
                    incluirAsociados // Pasar el flag al servicio
            );

            return reportes.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(reportes);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar reporte: " + e.getMessage());
        }
    }

    /**
     * Crea un nuevo viaje. Retorna 201 (Created) si es exitoso o 400 (Bad Request) si falla (RuntimeException).
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Viaje body) {
        try {
            ViajeDTO nuevoViaje = service.save(body);
            return new ResponseEntity<>(nuevoViaje, HttpStatus.CREATED);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Finaliza un viaje existente. Retorna 200 (OK) si es exitoso o 400 (Bad Request) si falla (RuntimeException).
     */
    @PatchMapping("/finalizar-viaje/{id}")
    public ResponseEntity<?> finalizarViaje(@PathVariable Long id){
        try {
            Map<String,Object> retorno = service.finalizarViaje(id);
            return new ResponseEntity<>(retorno, HttpStatus.OK);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina un viaje por ID. Retorna 204 (No Content) si es exitoso o 404 (Not Found) si falla.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}