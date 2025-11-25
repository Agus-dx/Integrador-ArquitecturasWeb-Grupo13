/**
 * 🎯 Componente de inicialización de datos (Data Seeding) para Spring Boot.
 *
 * Implementa CommandLineRunner para ejecutar la lógica de carga de viajes
 * de prueba en la base de datos tan pronto como la aplicación arranca,
 * asegurando datos consistentes para pruebas y desarrollo.
 */
package com.grupo13.microservicioviaje.utils;

import com.grupo13.microservicioviaje.model.Viaje;
import com.grupo13.microservicioviaje.repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CargarViajes implements CommandLineRunner {
    @Autowired
    private ViajeRepository viajeRepository;

    @Override
    public void run(String... args) {
        if (viajeRepository.count() > 0) return;

        System.out.println("Cargando datos iniciales de viajes...");
        cargarDatosIniciales();
        System.out.println("Viajes cargados correctamente!");
    }

    public void cargarDatosIniciales() {
        // Viaje 1: Usuario 1, Cuenta 1, completado
        Viaje v1 = new Viaje();
        v1.setParadaOrigen(1L);
        v1.setParadaDestino(2L);
        v1.setIdUsuario(1L);
        v1.setIdCuenta(1L);
        v1.setIdMonopatin("6912b90234e14ff3385b8da6");
        v1.setIdTarifa(1L);
        v1.setFechaInicio(LocalDateTime.now().minusDays(5));
        v1.setTiempoTotalMinutos(25);
        v1.setKilometrosRecorridos(5);
        v1.setTiempoPausa(0);
        v1.setActivo(true);
        viajeRepository.save(v1);

        // Viaje 2: Usuario 1, Cuenta 2 (premium), completado
        Viaje v2 = new Viaje();
        v2.setParadaOrigen(2L);
        v2.setParadaDestino(3L);
        v2.setIdUsuario(1L);
        v2.setIdCuenta(2L);
        v2.setIdMonopatin("6912b90234e14ff3385b8da7");
        v2.setIdTarifa(1L);
        v2.setFechaInicio(LocalDateTime.now().minusDays(4));
        v2.setTiempoTotalMinutos(40);
        v2.setKilometrosRecorridos(8);
        v2.setTiempoPausa(5);
        v2.setActivo(true);
        viajeRepository.save(v2);

        // Viaje 3: Usuario 3, Cuenta 4 (premium), completado
        Viaje v3 = new Viaje();
        v3.setParadaOrigen(1L);
        v3.setParadaDestino(3L);
        v3.setIdUsuario(3L);
        v3.setIdCuenta(4L);
        v3.setIdMonopatin("6912b90234e14ff3385b8da8");
        v3.setIdTarifa(2L);
        v3.setFechaInicio(LocalDateTime.now().minusDays(3));
        v3.setFechaFin(LocalDateTime.now().minusDays(3).plusMinutes(30));
        v3.setTiempoTotalMinutos(30);
        v3.setKilometrosRecorridos(6);
        v3.setTiempoPausa(0);
        v3.setActivo(false);
        viajeRepository.save(v3);

        // Viaje 4: Usuario 3, Cuenta 4 (premium), completado con pausa larga
        Viaje v4 = new Viaje();
        v4.setParadaOrigen(2L);
        v4.setParadaDestino(1L);
        v4.setIdUsuario(3L);
        v4.setIdCuenta(4L);
        v4.setIdMonopatin("6912b90234e14ff3385b8da9");
        v4.setIdTarifa(2L);
        v4.setFechaInicio(LocalDateTime.now().minusDays(2));
        v4.setFechaFin(LocalDateTime.now().minusDays(2).plusMinutes(50));
        v4.setTiempoTotalMinutos(50);
        v4.setKilometrosRecorridos(10);
        v4.setTiempoPausa(20); // Pausa excedida
        v4.setActivo(false);
        viajeRepository.save(v4);

        // Viaje 5: Usuario 4, Cuenta 5, completado
        Viaje v5 = new Viaje();
        v5.setParadaOrigen(3L);
        v5.setParadaDestino(1L);
        v5.setIdUsuario(4L);
        v5.setIdCuenta(5L);
        v5.setIdMonopatin("6912b90234e14ff3385b8daa");
        v5.setIdTarifa(3L);
        v5.setFechaInicio(LocalDateTime.now().minusDays(1));
        v5.setFechaFin(LocalDateTime.now().minusDays(1).plusMinutes(20));
        v5.setTiempoTotalMinutos(20);
        v5.setKilometrosRecorridos(4);
        v5.setTiempoPausa(0);
        v5.setActivo(false);
        viajeRepository.save(v5);

        // Viaje 6: Usuario 1, Cuenta 1, viaje largo completado
        Viaje v6 = new Viaje();
        v6.setParadaOrigen(1L);
        v6.setParadaDestino(2L);
        v6.setIdUsuario(1L);
        v6.setIdCuenta(1L);
        v6.setIdMonopatin("6912b90234e14ff3385b8dab");
        v6.setIdTarifa(3L);
        v6.setFechaInicio(LocalDateTime.now().minusHours(3));
        v6.setTiempoTotalMinutos(60);
        v6.setKilometrosRecorridos(15);
        v6.setTiempoPausa(10);
        v6.setActivo(true);
        viajeRepository.save(v6);

        // Viaje 7: Usuario 3, Cuenta 4, completado
        Viaje v7 = new Viaje();
        v7.setParadaOrigen(2L);
        v7.setParadaDestino(3L);
        v7.setIdUsuario(3L);
        v7.setIdCuenta(4L);
        v7.setIdMonopatin("6912b90234e14ff3385b8dac");
        v7.setIdTarifa(4L);
        v7.setFechaInicio(LocalDateTime.now().minusHours(5));
        v7.setTiempoTotalMinutos(30);
        v7.setKilometrosRecorridos(7);
        v7.setTiempoPausa(5);
        v7.setActivo(true);
        viajeRepository.save(v7);

        // Viaje 8: Usuario 1, Cuenta 1, corto completado
        Viaje v8 = new Viaje();
        v8.setParadaOrigen(3L);
        v8.setParadaDestino(2L);
        v8.setIdUsuario(1L);
        v8.setIdCuenta(1L);
        v8.setIdMonopatin("6912b90234e14ff3385b8dad");
        v8.setIdTarifa(4L);
        v8.setFechaInicio(LocalDateTime.now().minusHours(2));
        v8.setFechaFin(LocalDateTime.now().minusHours(1).minusMinutes(45));
        v8.setTiempoTotalMinutos(15);
        v8.setKilometrosRecorridos(3);
        v8.setTiempoPausa(0);
        v8.setActivo(false);
        viajeRepository.save(v8);

        // Viaje 9: Usuario 4, Cuenta 5, viaje reciente completado
        Viaje v9 = new Viaje();
        v9.setParadaOrigen(1L);
        v9.setParadaDestino(3L);
        v9.setIdUsuario(4L);
        v9.setIdCuenta(5L);
        v9.setIdMonopatin("6912b90234e14ff3385b8dae");
        v9.setIdTarifa(5L);
        v9.setFechaInicio(LocalDateTime.now().minusMinutes(45));
        v9.setFechaFin(LocalDateTime.now().minusMinutes(10));
        v9.setTiempoTotalMinutos(35);
        v9.setKilometrosRecorridos(9);
        v9.setTiempoPausa(3);
        v9.setActivo(false);
        viajeRepository.save(v9);

        // Viaje 10: Usuario 1, Cuenta 2 (premium), reciente completado
        Viaje v10 = new Viaje();
        v10.setParadaOrigen(2L);
        v10.setParadaDestino(1L);
        v10.setIdUsuario(1L);
        v10.setIdCuenta(2L);
        v10.setIdMonopatin("6912b90234e14ff3385b8daf");
        v10.setIdTarifa(5L);
        v10.setFechaInicio(LocalDateTime.now().minusMinutes(30));
        v10.setFechaFin(LocalDateTime.now().minusMinutes(5));
        v10.setTiempoTotalMinutos(25);
        v10.setKilometrosRecorridos(6);
        v10.setTiempoPausa(0);
        v10.setActivo(false);
        viajeRepository.save(v10);
    }
}
