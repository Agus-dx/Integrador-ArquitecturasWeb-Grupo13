package com.grupo13.microservicioparada.controllers;

import com.grupo13.microservicioparada.dtos.ParadaDTO;
import com.grupo13.microservicioparada.dtos.ParadaPatchDTO;
import com.grupo13.microservicioparada.model.Parada;
import com.grupo13.microservicioparada.services.ParadaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/paradas")
@RequiredArgsConstructor
public class ParadaController {

    private final ParadaService service;

    @GetMapping
    public ResponseEntity<List<ParadaDTO>> findALl() {
        List<ParadaDTO> paradas = service.findAll();

        if(paradas.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(paradas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParadaDTO> findById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(service.findById(id));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cercanas")
    public ResponseEntity<?> findParadasCercanas(
            @RequestParam(name = "latitud") Double latitud,
            @RequestParam(name = "longitud") Double longitud) {
        try {
            List<ParadaDTO> paradas = service.findParadasCercanas(latitud, longitud);
            if(paradas.isEmpty()){
                return new ResponseEntity<>("No hay paradas cercanas", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(paradas);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar paradas cercanas: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/monopatines/{id}")
    public ResponseEntity<?> findMonopatinesByParada(@PathVariable Long id,
                                                     @RequestParam(name= "estado", required = false) String estado){
        try {
            Map<String,Object> retorno;
            if(estado != null && !estado.trim().isEmpty()){
                retorno = service.findParadaConMonopatinesLibres(id);
            } else {
                retorno = service.findParadaConMonopatines(id);
            }

            if(retorno.get("monopatines").getClass().equals(String.class) ||
                    retorno.get("monopatinesLibres") != null && retorno.get("monopatinesLibres").getClass().equals(String.class)){
                return new ResponseEntity<>("Esta parada no tiene monopatines o no están libres",
                        HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok(retorno);
        }
        catch (Exception e) {
            // Asume que si falla en este punto (buscando la parada), es 404
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/monopatines-libres/{id}")
    public ResponseEntity<?> findMonopatinesLibresByParada(@PathVariable Long id){
        try {
            Map<String,Object> retorno = service.findParadaConMonopatinesLibres(id);

            if(retorno.get("monopatinesLibres").getClass().equals(String.class)){
                return new ResponseEntity<>(retorno.get("monopatinesLibres")
                        ,HttpStatus.NO_CONTENT);
            }

            return ResponseEntity.ok(retorno);
        }
        catch (Exception e) {
            // Asume que si falla en este punto (buscando la parada), es 404
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ParadaDTO> save(@RequestBody Parada parada) {
        try {
            ParadaDTO nuevaParada = service.save(parada);
            return new ResponseEntity<>(nuevaParada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody ParadaPatchDTO nuevaEditada) {
        try {
            ParadaDTO paradaEditada = service.patch(id,nuevaEditada);
            return ResponseEntity.ok(paradaEditada);
        }
        catch (Exception e) {
            // Intenta distinguir entre NotFound (404) y otros errores (400)
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("not found")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Error de validación o proceso
        }
    }
}
