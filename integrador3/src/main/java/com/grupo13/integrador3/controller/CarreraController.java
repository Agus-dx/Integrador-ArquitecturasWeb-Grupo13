package com.grupo13.integrador3.controller;

import com.grupo13.integrador3.service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/carreras")
public class CarreraController {
    @Autowired
    private CarreraService carreraService;

    /**
     * GET /carreras
     * Obtiene y retorna la lista completa de todas las carreras.
     */
    @GetMapping("")
    public ResponseEntity<?> getCarreras() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(carreraService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    /**
     * GET /carreras/tienen-estudiantes
     * Obtiene y retorna la lista de carreras que tienen al menos un estudiante matriculado.
     */
    @GetMapping("/tienen-estudiantes")
    public ResponseEntity<?> getCarrerasConEstudiantes() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(carreraService.getCarrerasConEstudiantes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }
}
