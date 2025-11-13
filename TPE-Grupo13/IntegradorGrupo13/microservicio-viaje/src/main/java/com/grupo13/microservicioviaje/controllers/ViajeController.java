package com.grupo13.microservicioviaje.controllers;

import com.grupo13.microservicioviaje.dtos.ReporteViajePeriodoDTO;
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
            @RequestParam(required = false, name = "cantidad") Integer cantidad,
            @RequestParam(required = false, name = "anioDesde") Integer anioDesde,
            @RequestParam(required = false, name = "anioHasta") Integer anioHasta,
            @RequestParam(required = false, name = "idUsuario") Long idUsuario) {

        try {
            if (anio != null && cantidad != null) {
                List<ReporteViajePeriodoDTO> reportes = service.getReporteViajeAnio(anio, cantidad);
                return reportes.isEmpty()
                        ? ResponseEntity.noContent().build()
                        : ResponseEntity.ok(reportes);
            }
            if (anioDesde != null && anioHasta != null && idUsuario != null) {
                Map<String, Object> reportes = service.getReportesUsuarioYasociadosPerido(idUsuario, anioDesde, anioHasta);
                return reportes.isEmpty()
                        ? ResponseEntity.noContent().build()
                        : ResponseEntity.ok(reportes);
            }
            if (anioDesde != null && anioHasta != null) {
                List<?> reportes = service.getReporteViajesPorUsuariosPeriodo(anioDesde, anioHasta);
                return reportes.isEmpty()
                        ? ResponseEntity.noContent().build()
                        : ResponseEntity.ok(reportes);
            }

            return ResponseEntity.badRequest()
                    .body("Parámetros inválidos. Opciones: (anio+cantidad) | (anioDesde+anioHasta+idUsuario) | (anioDesde+anioHasta)");

        } catch (Exception e) {
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