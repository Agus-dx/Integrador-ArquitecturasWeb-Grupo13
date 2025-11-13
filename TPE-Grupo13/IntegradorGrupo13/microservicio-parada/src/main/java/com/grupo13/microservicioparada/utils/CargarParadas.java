package com.grupo13.microservicioparada.utils;

import com.grupo13.microservicioparada.model.Parada;
import com.grupo13.microservicioparada.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CargarParadas implements CommandLineRunner {
    @Autowired
    private ParadaRepository repository;

    public void run(String... args) {
        if (this.repository.count() > 0) return;

        Parada p1 = new Parada();
        p1.setNombre("Parada 1");
        p1.setCiudad("Tandil");
        p1.setDireccion("Torrego");
        p1.setLatitud(70.4111111);
        p1.setLongitud(-3.722222);

        Parada p2 = new Parada();
        p2.setNombre("Parada 2");
        p2.setCiudad("Tandil");
        p2.setDireccion("Falucho");
        p2.setLatitud(40.422222);
        p2.setLongitud(-3.701111);

        Parada p3 = new Parada();
        p3.setNombre("Parada 3");
        p3.setCiudad("Tandil");
        p3.setDireccion("Sarmiento");
        p3.setLatitud(20.433333);
        p3.setLongitud(-3.700000);

        this.repository.save(p1);
        this.repository.save(p2);
        this.repository.save(p3);
    }

}
