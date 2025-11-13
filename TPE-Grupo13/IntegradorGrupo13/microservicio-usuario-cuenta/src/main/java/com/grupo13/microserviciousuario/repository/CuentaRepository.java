package com.grupo13.microserviciousuario.repository;

import com.grupo13.microserviciousuario.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta,Long> {
}
