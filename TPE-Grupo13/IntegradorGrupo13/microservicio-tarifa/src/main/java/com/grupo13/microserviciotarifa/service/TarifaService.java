package com.grupo13.microserviciotarifa.service;

import com.grupo13.microserviciotarifa.dto.TarifaDTO;
import com.grupo13.microserviciotarifa.entity.Tarifa;
import com.grupo13.microserviciotarifa.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TarifaService {

    @Autowired
    private TarifaRepository tarifaRepository;

    @Transactional
    public TarifaDTO actualizarTarifa(Tarifa tarifa) {
        Tarifa tarifaGuardada = tarifaRepository.save(tarifa);
        return new TarifaDTO(tarifaGuardada);
    }

    @Transactional
    public void eliminarTarifa(Long id) {
        tarifaRepository.deleteById(id);
    }

    @Transactional
    public TarifaDTO crearTarifa(Tarifa tarifa) {
        Tarifa tarifaGuardada = tarifaRepository.save(tarifa);
        return new TarifaDTO(tarifaGuardada);
    }

    @Transactional
    public List<Tarifa> findAllTarifas() {
        return tarifaRepository.findAll();
    }

    @Transactional
    public TarifaDTO findTarifaById(Long id) {
        Tarifa tarifa = tarifaRepository.findById(id).orElse(null);
        if (tarifa == null) {
            return null;
        }
        return new TarifaDTO(tarifa);
    }

    @Transactional
    public TarifaDTO findTarifaByFecha(Date fecha) {
        Tarifa tarifa = tarifaRepository.findByFecha(fecha);
        if (tarifa == null) {
            return null;
        }
        return new TarifaDTO(tarifa);
    }

    @Transactional
    public List<TarifaDTO> findTarifaByMonto(Double monto) {
        Tarifa tarifa = tarifaRepository.findByMonto(monto);
        if (tarifa == null) {
            return List.of();
        }
        return List.of(new TarifaDTO(tarifa));
    }

    @Transactional
    public TarifaDTO actualizarTarifaDesdeFecha(Date fecha, double porcentajeIncremento) {
        System.out.println("Buscando tarifa vigente desde: " + fecha);
        List<Tarifa> todasLasTarifas = tarifaRepository.findAll();
        System.out.println("Total de tarifas en BD: " + todasLasTarifas.size());
        todasLasTarifas.forEach(t -> {
            System.out.println("  - ID: " + t.getId() + ", Fecha: " + t.getFecha() +
                    " (comparando con " + fecha + " -> " + (t.getFecha().compareTo(fecha) <= 0 ? "VÁLIDA" : "FUTURA") + ")");
        });

        List<Tarifa> tarifasVigentes = tarifaRepository.findTarifasVigentesDesde(fecha);
        System.out.println("Tarifas vigentes encontradas: " + tarifasVigentes.size());

        if (tarifasVigentes.isEmpty()) {
            throw new RuntimeException("No se encontró tarifa vigente para la fecha: " + fecha +
                    ". Tarifas disponibles: " + todasLasTarifas.size());
        }

        Tarifa tarifa = tarifasVigentes.get(0); // Tomar la primera (más reciente)

        System.out.println("Tarifa encontrada - ID: " + tarifa.getId() + ", Fecha: " + tarifa.getFecha() + ", Monto actual: " + tarifa.getMonto());

        // Aplicar el incremento porcentual
        double montoOriginal = tarifa.getMonto();
        double nuevoMonto = montoOriginal * (1 + porcentajeIncremento / 100.0);

        System.out.println("Aplicando incremento de " + porcentajeIncremento + "%");
        System.out.println("Monto original: " + montoOriginal + " -> Nuevo monto: " + nuevoMonto);

        tarifa.setMonto(nuevoMonto);

        // Si también hay montoExtra, aplicar el mismo incremento
        double montoExtraOriginal = tarifa.getMontoExtra();
        double nuevoMontoExtra = montoExtraOriginal * (1 + porcentajeIncremento / 100.0);
        tarifa.setMontoExtra(nuevoMontoExtra);
        System.out.println("MontoExtra actualizado: " + montoExtraOriginal + " -> " + nuevoMontoExtra);

        Tarifa tarifaGuardada = tarifaRepository.save(tarifa);
        System.out.println("Tarifa guardada con nuevo monto: " + tarifaGuardada.getMonto());

        return new TarifaDTO(tarifaGuardada);
    }
}
