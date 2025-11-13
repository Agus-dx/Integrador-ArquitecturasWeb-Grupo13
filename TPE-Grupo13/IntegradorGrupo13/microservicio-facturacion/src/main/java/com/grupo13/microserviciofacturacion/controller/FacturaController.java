package com.grupo13.microserviciofacturacion.controller;

import com.grupo13.microserviciofacturacion.dto.TotalFacturadoDTO;
import com.grupo13.microserviciofacturacion.dto.ViajeFacturaRequestDTO;
import com.grupo13.microserviciofacturacion.entity.Factura;
import com.grupo13.microserviciofacturacion.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping("/")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(facturaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facturaService.findById(id));
    }

    @GetMapping("/entre-fechas")
    public ResponseEntity<?> findByFechaBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin) {
        return ResponseEntity.status(HttpStatus.OK).body(facturaService.findByFechaBetween(fechaInicio, fechaFin));
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody Factura factura) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facturaService.save(factura));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        facturaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/total-facturado")
    public ResponseEntity<?> getTotalFacturado(
            @RequestParam int anio,
            @RequestParam int mesDesde,
            @RequestParam int mesHasta) {
        if (mesDesde < 1 || mesDesde > 12 || mesHasta < 1 || mesHasta > 12 || mesDesde > mesHasta) {
            return ResponseEntity.badRequest().body("Meses inválidos. Deben estar entre 1-12 y mesDesde <= mesHasta");
        }
        TotalFacturadoDTO total = facturaService.getTotalFacturadoPorRangoMeses(anio, mesDesde, mesHasta);
        return ResponseEntity.ok(total);
    }

    @PostMapping("/viaje")
    public ResponseEntity<?> facturarViaje(@RequestBody ViajeFacturaRequestDTO request) {
        try {
            Factura factura = facturaService.crearFacturaDesdeViaje(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(factura);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear factura: " + e.getMessage());
        }
    }
}
