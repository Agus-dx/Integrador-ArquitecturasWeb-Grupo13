package com.grupo13.microserviciousuario.controllers;

import com.grupo13.microserviciousuario.entity.Cuenta;
import com.grupo13.microserviciousuario.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public List<Cuenta> findAll() {
        return cuentaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> findById(@PathVariable Long id) {
        Cuenta cuenta = cuentaService.findById(id);
        if (cuenta != null) {
            return ResponseEntity.ok(cuenta);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cuenta> save(@RequestBody Cuenta cuenta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaService.save(cuenta));
    }
    @PatchMapping("/{id}/cambiar-estado-cuenta")
    public ResponseEntity<Cuenta> cambiarEstadoCuenta(@PathVariable Long id) {
        Cuenta cuentaActualizada = cuentaService.cambiarEstadoCuenta(id);
        if (cuentaActualizada != null) {
            return ResponseEntity.ok(cuentaActualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/restar-saldo/{id}")
    public ResponseEntity<?> restarSaldoCuenta(@PathVariable Long id,
                                               @RequestParam(name="montoRestar") BigDecimal montoArestar) {
        try {
            Cuenta cuentaActualizada = cuentaService.restarSaldoCuenta(id, montoArestar);
            return ResponseEntity.ok(cuentaActualizada);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
