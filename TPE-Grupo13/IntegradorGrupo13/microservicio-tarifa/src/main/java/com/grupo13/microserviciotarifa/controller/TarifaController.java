package com.grupo13.microserviciotarifa.controller;

import com.grupo13.microserviciotarifa.dto.TarifaDTO;
import com.grupo13.microserviciotarifa.entity.Tarifa;
import com.grupo13.microserviciotarifa.service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/tarifas") //http://localhost:8080/api/tarifas/
public class TarifaController {

    @Autowired
    private TarifaService tarifaService;

    @PostMapping("/ajustar-precio")
    public ResponseEntity<?> ajustarPrecioDesde(@RequestBody Tarifa nuevaTarifa) {
        try {
            TarifaDTO tarifaCreada = tarifaService.crearTarifa(nuevaTarifa);
            return ResponseEntity.status(HttpStatus.CREATED).body(tarifaCreada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al ajustar precio: " + e.getMessage());
        }
    }

    @PostMapping("/actualizar-desde-fecha")
    public ResponseEntity<?> actualizarTarifaDesdeFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fecha,
            @RequestParam double porcentajeIncremento) {
        try {
            System.out.println("Fecha recibida: " + fecha);
            System.out.println("Porcentaje recibido: " + porcentajeIncremento);
            TarifaDTO resultado = tarifaService.actualizarTarifaDesdeFecha(fecha, porcentajeIncremento);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage() + " - Causa: " + (e.getCause() != null ? e.getCause().getMessage() : "N/A"));
        }
    }

    @PostMapping("/actualizar")
    public ResponseEntity<?> actualizarTarifa(@RequestBody Tarifa tarifa) {
        return ResponseEntity.ok(tarifaService.actualizarTarifa(tarifa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTarifa(@PathVariable Long id) {
        tarifaService.eliminarTarifa(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearTarifa(@RequestBody Tarifa tarifa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tarifaService.crearTarifa(tarifa));
    }

    @GetMapping("/")
    public ResponseEntity<?> findAllTarifas() {
        return ResponseEntity.status(HttpStatus.OK).body(tarifaService.findAllTarifas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findTarifaById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(tarifaService.findTarifaById(id));
    }
}
