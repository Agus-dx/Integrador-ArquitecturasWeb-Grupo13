package com.grupo13.integrador3.controller;

import com.grupo13.integrador3.dto.ReporteDTO;
import com.grupo13.integrador3.service.EstudianteCarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiantes-carreras")
public class EstudianteCarreraController {
    @Autowired
    private EstudianteCarreraService estudianteCarreraService;

    /**
     * GET /estudiantes-carreras/reportes
     * Obtiene un reporte anualizado de las carreras y la cantidad de inscriptos y egresados.
     */
    @GetMapping("/reportes")
    public ResponseEntity<?> getReportes() {
        try{
            List<ReporteDTO> reportes = estudianteCarreraService.getReportes();
            if(!reportes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(reportes);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay reportes validos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    /**
     * POST /estudiantes-carreras/matricular?estudianteId={id}&carreraId={id}
     * Matricula un estudiante existente en una carrera existente.
     */
    @PostMapping("/matricular")
    public ResponseEntity<String> matricularEstudiante(@RequestParam Long estudianteId, @RequestParam Long carreraId) {
        try {
            estudianteCarreraService.matricularEstudiante(estudianteId, carreraId);
            return ResponseEntity.status(HttpStatus.OK).body("Estudiante matriculado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }


}
