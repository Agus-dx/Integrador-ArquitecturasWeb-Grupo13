/**
 * 🛴 Controlador REST (API Layer) para la gestión de Monopatines.
 *
 * Es el punto de entrada HTTP principal para la administración de los monopatines
 * como activos móviles del sistema. Expone endpoints críticos para:
 * 1. CRUD básico y Actualizaciones de Estado/Ubicación (POST, DELETE, PATCH, PUT /estado/{estado}).
 * 2. **Integración con Paradas:** (GET /parada/{idParada}) Endpoint consumido por
 * el Microservicio de Paradas para obtener el inventario de vehículos en una
 * estación específica, con filtrado opcional por estado (ej. "LIBRE").
 * 3. **Mantenimiento y Reportes:** (GET /reportes-mantenimiento/{kmMaximo})
 * Genera listas de monopatines que superan un umbral de kilometraje, crucial
 * para la logística de mantenimiento.
 */
package com.grupo13.microserviciomonopatin.controllers;

import com.grupo13.microserviciomonopatin.dtos.MonopatinDTO;
import com.grupo13.microserviciomonopatin.dtos.MonopatinPatchDTO;
import com.grupo13.microserviciomonopatin.dtos.ReporteMantenimientoDTO;
import com.grupo13.microserviciomonopatin.model.Monopatin;
import com.grupo13.microserviciomonopatin.services.MonopatinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/monopatines")
@RequiredArgsConstructor
public class MonopatinController {

    private final MonopatinService service;

    /**
     * Obtiene todos los monopatines. Retorna 204 (No Content) si la lista está vacía.
     */
    @GetMapping
    public ResponseEntity<List<MonopatinDTO>> findAll() {
        List<MonopatinDTO> monopatines = service.findAll();

        if (monopatines.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(monopatines);
    }

    /**
     * Busca un monopatin por su ID. Retorna 404 (Not Found) si falla.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MonopatinDTO> findById(@PathVariable String id) {
        try {
            MonopatinDTO monopatin = service.findById(id);
            return ResponseEntity.ok(monopatin);
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene un monopatin y la parada asociada (probablemente para mapeo o contexto).
     */
    @GetMapping("/{id}/parada")
    public ResponseEntity<Map<String, Object>> getMonopatinConParada(@PathVariable String id) {
        return ResponseEntity.ok(service.getMonopatinConParada(id));
    }

    /**
     * Genera reportes de monopatines que superan un kilometraje máximo (kmMaximo).
     * Retorna 204 si no hay reportes.
     */
    @GetMapping("/reportes-mantenimiento/{kmMaximo}")
    public ResponseEntity<List<ReporteMantenimientoDTO>> getMonopatinesManetinimiento(
            @PathVariable Integer kmMaximo) {
        List<ReporteMantenimientoDTO> reportes = service.getReportesMantenimiento(kmMaximo);

        if(reportes.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reportes);
    }

    /**
     * Crea un nuevo monopatin. Retorna 201 (Created) si es exitoso o 400 (Bad Request) si falla.
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Monopatin monopatin) {
        try {
            MonopatinDTO nuevoMonopatin = service.save(monopatin);
            return new ResponseEntity<>(nuevoMonopatin, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error al guardar el monopatin: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina un monopatin por su ID. Retorna 204 (No Content) si es exitoso o 404 si no existe.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Realiza una actualización parcial de un monopatin (PATCH).
     * Retorna 404 si no lo encuentra o 400 si hay otro error.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdate(@PathVariable String id, @RequestBody MonopatinPatchDTO edit) {
        try {
            MonopatinDTO monopatinEditado = service.patch(id, edit);
            return ResponseEntity.ok(monopatinEditado);
        }
        catch (Exception e) {
            // Intenta distinguir entre NotFound (404) y otros errores (400) basado en el mensaje
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("not found")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Error al actualizar: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Actualiza el estado de un monopatin por ID.
     * Retorna 404 si no lo encuentra o 400 si el estado es inválido.
     */
    @PutMapping("/{id}/estado/{estado}")
    public ResponseEntity<?> setEstado(@PathVariable String id, @PathVariable String estado) {
        try {
            MonopatinDTO monopatinEditado = service.setEstadoMonopatin(id, estado);
            return ResponseEntity.ok(monopatinEditado);
        }
        catch (Exception e) {
            String errorMsg = e.getMessage() != null ? e.getMessage() : "Error desconocido.";

            if (errorMsg.toLowerCase().contains("no encontrado") || errorMsg.toLowerCase().contains("not found")) {
                return new ResponseEntity<>(errorMsg, HttpStatus.NOT_FOUND); // 404
            }
            // Asume que cualquier otro error es un estado inválido o similar (400)
            return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST); // 400
        }
    }

    /**
     * Busca monopatines en una parada por ID, opcionalmente filtrando por estado.
     */
    @GetMapping("/parada/{idParada}")
    public ResponseEntity<List<MonopatinDTO>> findMonopatinesByIdParada(@PathVariable Long idParada,
                                                                        @RequestParam(name= "estado", required = false) String estado){
        List<MonopatinDTO> monopatinesEnParada = new ArrayList<>();
        if(estado!=null && !estado.trim().isEmpty()){
            monopatinesEnParada.addAll(
                    service.findMonopatinesPorEstadoByIdParada(idParada,estado));
        }
        else{
            monopatinesEnParada.addAll(service.findMonopatinesByIdParada(idParada));
        }

        return ResponseEntity.ok(monopatinesEnParada);
    }



}