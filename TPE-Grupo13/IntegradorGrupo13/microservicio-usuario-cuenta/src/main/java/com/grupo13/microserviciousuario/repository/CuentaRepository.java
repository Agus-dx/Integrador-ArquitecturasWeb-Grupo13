/**
 * 💾 Repositorio de Spring Data JPA para la entidad Cuenta.
 *
 * Extiende JpaRepository, proporcionando automáticamente todos los métodos
 * CRUD básicos (Crear, Leer, Actualizar, Borrar) y operaciones de paginación/ordenación
 * necesarios para interactuar con la tabla 'Cuenta' en la base de datos.
 * Es la capa que aísla el servicio de la lógica de persistencia.
 */
package com.grupo13.microserviciousuario.repository;

import com.grupo13.microserviciousuario.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta,Long> {
}
