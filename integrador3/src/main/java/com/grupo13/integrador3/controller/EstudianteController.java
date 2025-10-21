package com.grupo13.integrador3.controller;

import com.grupo13.integrador3.dto.EstudianteDTO;
import com.grupo13.integrador3.model.Estudiante;
import com.grupo13.integrador3.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {
    @Autowired
    private EstudianteService estudianteService;

    /**
     * GET /estudiantes
     * Obtiene y retorna la lista completa de todos los estudiantes.
     */
    @GetMapping("")
    public ResponseEntity<?> getEstudiantes() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(estudianteService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error.\"}");
        }

    }

    /**
     * GET /estudiantes/{id}
     * Busca y retorna un estudiante específico por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getEstudianteById(@PathVariable Long id) {
        try {
            EstudianteDTO estudiante = estudianteService.findById(id);
            if (estudiante == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudiante no encontrado");
            }
            return ResponseEntity.status(HttpStatus.OK).body(estudiante);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error.\"}");
        }
    }

    /**
     * GET /estudiantes/ordenar?criterio={campo}
     * criterios: "dni", "nombre","apellido", "numero_libreta", "genero", "edad", "ciudad"
     * Obtiene la lista de estudiantes ordenada por un campo especificado (criterio).
     */
    @GetMapping("/ordenar")
    public ResponseEntity<?> getEstudiantesOrderBy(@RequestParam String criterio) {
        try {
            List<EstudianteDTO> estudiantes = estudianteService.getEstudiantesOrderBy(criterio);
            if (estudiantes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Criterio de ordenamiento no valido");
            }
            return ResponseEntity.status(HttpStatus.OK).body(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error.\"}");
        }
    }

    /**
     * GET /estudiantes/numeroLibreta/{numeroLibreta}
     * Busca y retorna un estudiante por su número de libreta universitaria.
     */
    @GetMapping("/numeroLibreta/{numeroLibreta}")
    public ResponseEntity<?> getEstudianteByNumeroLibreta(@PathVariable Long numeroLibreta) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(estudianteService.findEstudianteByNumeroLibreta(numeroLibreta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar estudiante: " + e.getMessage());
        }
    }

    /**
     * GET /estudiantes/genero/{genero}
     * Obtiene y retorna todos los estudiantes de un género específico.
     */
    @GetMapping("/genero/{genero}")
    public ResponseEntity<?> getEstudiantesByGenero(@PathVariable String genero) {
        try {
            List<EstudianteDTO> estudiantes = estudianteService.findEstudiantesByGenero(genero);
            if (estudiantes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontraron estudiantes con el genero: " + genero);
            }
            return ResponseEntity.status(HttpStatus.OK).body(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener estudiantes: " + e.getMessage());
        }
    }

    /**
     * GET /estudiantes/filtro?carrera={nombre}&ciudadOrigen={ciudad}
     * Filtra y retorna los estudiantes matriculados en una carrera y con una ciudad de origen específica.
     */
    @GetMapping("/filtro")
    public ResponseEntity<?> getEstudiantesByCarreraAndCiudadOrigen(
            @RequestParam String carrera,
            @RequestParam String ciudadOrigen) {
        try {
            List<EstudianteDTO> estudiantes = estudianteService.findEstudiantesByCarreraAndCiudadOrigen(carrera, ciudadOrigen);
            if (estudiantes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontraron estudiantes de la carrera " + carrera + " de la ciudad " + ciudadOrigen);
            }
            return ResponseEntity.status(HttpStatus.OK).body(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener estudiantes: " + e.getMessage());
        }
    }

    /**
     * POST /estudiantes
     * Agrega un nuevo estudiante a la base de datos.
     */
    @PostMapping("")
    public ResponseEntity<?> addEstudiante(@RequestBody Estudiante estudiante) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(estudianteService.addEstudiante(estudiante));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo ingresar, revise los campos e intente nuevamente.\"}");
        }
    }

}