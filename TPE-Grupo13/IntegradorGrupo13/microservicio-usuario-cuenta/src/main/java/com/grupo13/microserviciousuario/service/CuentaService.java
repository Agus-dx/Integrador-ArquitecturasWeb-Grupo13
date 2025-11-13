package com.grupo13.microserviciousuario.service;

import com.grupo13.microserviciousuario.entity.Cuenta;
import com.grupo13.microserviciousuario.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Transactional(readOnly = true)
    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    /**
     * Busca una cuenta por ID. Lanza RuntimeException si no la encuentra.
     */
    @Transactional(readOnly = true)
    public Cuenta findById(Long id) {
        // Se asegura de lanzar una excepción si no existe (mejor que devolver null)
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));
    }

    @Transactional
    public Cuenta save(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Transactional
    public void delete(Long id) {
        // Se asegura que la cuenta exista antes de intentar eliminar
        if (!cuentaRepository.existsById(id)) {
            throw new RuntimeException("Cuenta no encontrada con ID: " + id);
        }
        cuentaRepository.deleteById(id);
    }

    /**
     * Cambia el estado de una cuenta (ACTIVA <-> SUSPENDIDA).
     * Lanza RuntimeException si no encuentra la cuenta.
     */
    @Transactional
    public Cuenta cambiarEstadoCuenta(Long id){
        // Lanza RuntimeException si no encuentra la cuenta
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));

        if(cuenta.getEstado() == com.grupo13.microserviciousuario.entity.EstadoCuenta.ACTIVA){
            cuenta.setEstado(com.grupo13.microserviciousuario.entity.EstadoCuenta.SUSPENDIDA);
        } else if (cuenta.getEstado() == com.grupo13.microserviciousuario.entity.EstadoCuenta.SUSPENDIDA) {
            cuenta.setEstado(com.grupo13.microserviciousuario.entity.EstadoCuenta.ACTIVA);
        }
        return cuentaRepository.save(cuenta);
    }

    /**
     * Resta un monto al saldo de la cuenta.
     * Lanza RuntimeException si no encuentra la cuenta.
     * Lanza IllegalArgumentException si el saldo es insuficiente.
     */
    @Transactional
    public Cuenta restarSaldoCuenta(Long id, BigDecimal montoArestar) {
        // 1. Verifica la existencia (NotFound)
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));
        // 2. Verifica saldo insuficiente (IllegalArgument)
        if(cuenta.getSaldo().compareTo(montoArestar) < 0){
            String msg = String.format("Saldo insuficiente. Saldo actual: %s, Monto a restar: %s",
                    cuenta.getSaldo().toPlainString(), montoArestar.toPlainString());
            throw new IllegalArgumentException(msg);
        }
        BigDecimal nuevoSaldo = cuenta.getSaldo().subtract(montoArestar);
        cuenta.setSaldo(nuevoSaldo);
        return cuentaRepository.save(cuenta);
    }
}