package com.grupo13.microservicioadmin.controllers;
import java.util.Date;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

/*
a. Como administrador quiero poder generar un reporte de uso de monopatines por kilómetros
para establecer si un monopatín requiere de mantenimiento. Este reporte debe poder
configurarse para incluir (o no) los tiempos de pausa.
b. Como administrador quiero poder anular cuentas de usuarios, para inhabilitar el uso
momentáneo de la aplicación.
c. Como administrador quiero consultar los monopatines con más de X viajes en un cierto año.
d. Como administrador quiero consultar el total facturado en un rango de meses de cierto año.
e. Como administrador quiero ver los usuarios que más utilizan los monopatines, filtrado por
período y por tipo de usuario.
f. Como administrador quiero hacer un ajuste de precios, y que a partir de cierta fecha el sistema
habilite los nuevos precios.
*/

// EN PROCESO - EN PROCESO - EN PROCESO - EN PROCESO - EN PROCESO - EN PROCESO - EN PROCESO

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    /*
    @GetMapping("/")
    public String getAdmin() {
    }

    //a
    @GetMapping("/reportes/monopatines")
    public String generarReporteMonopatines() {
    }

    /*
    //b
    @GetMapping("//{id}/anular")
    public String anularUsuario(@PathVariable String id) {
    }


    //c
    @GetMapping("/monopatines-viajes")
    public String consultarMonopatinesPorViajes(
            @RequestParam int cantidadViajes,
            @RequestParam int anio) {
    }
    //d
    @GetMapping("/facturacion")
    public String consultarTotalFacturado(
            @RequestParam int anio,
            @RequestParam int mesInicio,
            @RequestParam int mesFin) {
    }
    //e
    @GetMapping("/usuarios/mas-utilizan")
    public String verUsuariosTop(
            @RequestParam String periodo,
            @RequestParam String tipoUsuario) {}

    //f
    @GetMapping("/ajuste-precios")
    public String hacerAjustePrecios(
            @RequestParam double nuevosPrecios,
            @RequestParam Date fechaInicio) {
    }*/
}

