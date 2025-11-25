/**
 * 💾 Repositorio de Spring Data JPA para la entidad Usuario.
 *
 * Extiende JpaRepository para proporcionar métodos CRUD básicos.
 * Define una consulta JPQL personalizada (@Query) que es crítica para la
 * validación inter-servicio:
 * - existsCuentaInUsuario: Verifica si una Cuenta específica está asociada
 * a un Usuario específico, navegando la relación @ManyToMany.
 */
package com.grupo13.microserviciousuario.repository;

import com.grupo13.microserviciousuario.dtos.LoginDTO;
import com.grupo13.microserviciousuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = """
        SELECT CASE WHEN EXISTS (
            SELECT c 
            FROM Usuario u JOIN u.cuentas c
            WHERE u.id = :idUsuario AND c.id = :idCuenta
        ) 
        THEN TRUE ELSE FALSE END
        """)
    boolean existsCuentaInUsuario(Long idUsuario, Long idCuenta);


    @Query("""
            SELECT u.id
            FROM Usuario u
            WHERE u.rol = UPPER(:rol) 
            """)
    Set<Long> getUsuariosByRol(String rol);

    @Query(value= """
                 SELECT new com.grupo13.microserviciousuario.dtos.LoginDTO(u.id,u.email,u.password,u.rol)
                 FROM Usuario u
                 WHERE LOWER(u.email) = LOWER(:username)
                 """)
    Optional<LoginDTO> getUsuarioByUsername(String username);

    @Query("SELECT u FROM Usuario u JOIN u.cuentas c WHERE c.id = :idCuenta")
    Set<Usuario> findUsuariosByCuentaId(@Param("idCuenta") Long idCuenta);
}
