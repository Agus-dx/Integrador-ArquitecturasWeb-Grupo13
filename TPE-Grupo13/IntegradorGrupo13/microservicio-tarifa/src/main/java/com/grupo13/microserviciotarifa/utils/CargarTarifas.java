package com.grupo13.microserviciotarifa.utils;

import com.grupo13.microserviciotarifa.entity.Tarifa;
import com.grupo13.microserviciotarifa.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class CargarTarifas implements CommandLineRunner {

    @Autowired
    private TarifaRepository tarifaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (tarifaRepository.count() == 0) {
            System.out.println("Cargando tarifas de prueba...");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Tarifa tarifa1 = new Tarifa();
            tarifa1.setMonto(60.0);
            tarifa1.setMontoExtra(150.0);
            tarifa1.setFecha(sdf.parse("2025-01-01"));
            tarifa1.setCuotaMensualPremium(5000.0);
            tarifa1.setPorcentajeRecargoPausa(20.0);
            tarifa1.setTiempoMaximoPausaMinutos(15);
            tarifaRepository.save(tarifa1);

            Tarifa tarifa2 = new Tarifa();
            tarifa2.setMonto(65.0);
            tarifa2.setMontoExtra(155.0);
            tarifa2.setFecha(sdf.parse("2025-02-01"));
            tarifa2.setCuotaMensualPremium(5200.0);
            tarifa2.setPorcentajeRecargoPausa(22.0);
            tarifa2.setTiempoMaximoPausaMinutos(15);
            tarifaRepository.save(tarifa2);

            Tarifa tarifa3 = new Tarifa();
            tarifa3.setMonto(70.0);
            tarifa3.setMontoExtra(160.0);
            tarifa3.setFecha(sdf.parse("2025-03-01"));
            tarifa3.setCuotaMensualPremium(5400.0);
            tarifa3.setPorcentajeRecargoPausa(25.0);
            tarifa3.setTiempoMaximoPausaMinutos(15);
            tarifaRepository.save(tarifa3);

            System.out.println("Tarifas cargadas correctamente!");
        }
    }
}
