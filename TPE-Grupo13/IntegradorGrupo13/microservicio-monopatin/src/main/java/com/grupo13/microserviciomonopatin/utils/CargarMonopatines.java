package com.grupo13.microserviciomonopatin.utils;

import com.grupo13.microserviciomonopatin.model.Estado;
import com.grupo13.microserviciomonopatin.model.Monopatin;
import com.grupo13.microserviciomonopatin.repository.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CargarMonopatines implements CommandLineRunner {

    @Autowired
    private MonopatinRepository repository;

    @Override
    public void run(String... args) {
        if(this.repository.count() > 0) return;

        Monopatin m1 = new Monopatin();
        m1.setEstado(Estado.LIBRE);
        m1.setLatitud(40.428010);
        m1.setLongitud(-3.695500);
        m1.setIdParada(2L);
        m1.setKmRecorridos(55);
        m1.setTiempoUsado(25);
        repository.save(m1);

        Monopatin m2 = new Monopatin();
        m2.setEstado(Estado.ACTIVO);
        m2.setLatitud(40.410500);
        m2.setLongitud(-3.712100);
        m2.setIdParada(3L);
        m2.setKmRecorridos(410);
        m2.setTiempoUsado(180);
        repository.save(m2);

        Monopatin m3 = new Monopatin();
        m3.setEstado(Estado.LIBRE);
        m3.setLatitud(40.415050);
        m3.setLongitud(-3.698000);
        m3.setIdParada(4L);
        m3.setKmRecorridos(120);
        m3.setTiempoUsado(50);
        repository.save(m3);

        Monopatin m4 = new Monopatin();
        m4.setEstado(Estado.MANTENIMIENTO);
        m4.setLatitud(40.422300);
        m4.setLongitud(-3.700100);
        m4.setIdParada(1L);
        m4.setKmRecorridos(750);
        m4.setTiempoUsado(380);
        repository.save(m4);

        Monopatin m5 = new Monopatin();
        m5.setEstado(Estado.LIBRE);
        m5.setLatitud(40.417800);
        m5.setLongitud(-3.705800);
        m5.setIdParada(3L);
        m5.setKmRecorridos(180);
        m5.setTiempoUsado(75);
        repository.save(m5);

        Monopatin m6 = new Monopatin();
        m6.setEstado(Estado.MANTENIMIENTO);
        m6.setLatitud(40.420800);
        m6.setLongitud(-3.707500);
        m6.setIdParada(1L);
        m6.setKmRecorridos(900);
        m6.setTiempoUsado(450);
        repository.save(m6);

        Monopatin m7 = new Monopatin();
        m7.setEstado(Estado.ACTIVO);
        m7.setLatitud(40.413000);
        m7.setLongitud(-3.715000);
        m7.setIdParada(3L);
        m7.setKmRecorridos(290);
        m7.setTiempoUsado(130);
        repository.save(m7);

        this.repository.save(m1);
        this.repository.save(m2);
        this.repository.save(m3);
        this.repository.save(m4);
        this.repository.save(m5);
        this.repository.save(m6);
        this.repository.save(m7);
    }
}
