package com.grupo13.microserviciousuario.repository;

import com.grupo13.microserviciousuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("""
        SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END
        FROM Usuario u JOIN u.cuentas c
        WHERE u.id = :idUsuario AND c.id = :idCuenta
        """)
    boolean existsCuentaInUsuario(Long idUsuario, Long idCuenta);
}
