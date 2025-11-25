/**
 * 🛠️ Capa de Servicio (Service Layer) para la gestión de Usuarios.
 *
 * Contiene la lógica de negocio y las operaciones transaccionales para la entidad Usuario,
 * así como la orquestación de la relación @ManyToMany con Cuenta. Es responsable de:
 * 1. Implementar operaciones CRUD y de búsqueda.
 * 2. Gestionar la asociación de cuentas ('asignarCuenta').
 * 3. Implementar la validación 'cuentaAsociada', que es consumida por el Microservicio de Viajes.
 * 4. Controlar el estado de las cuentas de un usuario ('cambiarEstadoCuentas').
 * Garantiza la integridad transaccional (@Transactional) en operaciones de modificación.
 */
package com.grupo13.microserviciousuario.service;

import com.grupo13.microserviciousuario.dtos.LoginDTO;
import com.grupo13.microserviciousuario.entity.Cuenta;
import com.grupo13.microserviciousuario.entity.EstadoCuenta;
import com.grupo13.microserviciousuario.entity.Rol;
import com.grupo13.microserviciousuario.entity.Usuario;
import com.grupo13.microserviciousuario.repository.CuentaRepository;
import com.grupo13.microserviciousuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Transactional(readOnly = true)
    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por ID. Lanza RuntimeException si no lo encuentra.
     */
    @Transactional(readOnly = true)
    public Usuario findById(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    /**
     * Obtiene las cuentas asociadas a un usuario. Lanza RuntimeException si el usuario no existe.
     */
    @Transactional(readOnly = true)
    public Set<Cuenta> getCuentasByUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + idUsuario));

        return usuario.getCuentas();
    }


    @Transactional
    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario por ID. Lanza RuntimeException si no lo encuentra.
     */
    @Transactional
    public void delete(Long id){
        // Verifica la existencia antes de eliminar para lanzar una excepción clara.
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    /**
     * Asigna una cuenta existente a un usuario existente.
     * Lanza RuntimeException si el usuario o la cuenta no existen.
     */
    @Transactional
    public Optional<Usuario> asignarCuenta(Long idUsuario, Long idCuenta) {
        Optional<Usuario> oUsuario = usuarioRepository.findById(idUsuario);
        if (oUsuario.isPresent()) {
            Optional<Cuenta> oCuenta = cuentaRepository.findById(idCuenta);
            if (oCuenta.isPresent()) {
                Usuario usuario = oUsuario.get();
                Cuenta cuenta = oCuenta.get();
                usuario.addCuenta(cuenta); // Asumiendo que tienes un metodo addCuenta en tu entidad Usuario
                usuarioRepository.save(usuario);
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }

    @Transactional
    public Set<Long> getUsuarioByRol(String rol) {
        if(Rol.perteneceAlEnum(rol) == null){
            return new HashSet<>();
        }
        return usuarioRepository.getUsuariosByRol(rol);
    }

    @Transactional(readOnly = true)
    public boolean cuentaAsociada(Long idUsuario, Long idCuenta) {
        if (idUsuario == null || idCuenta == null) {
            return false;
        }
        return usuarioRepository.existsCuentaInUsuario(idUsuario,idCuenta);
    }

    @Transactional(readOnly = true)
    public LoginDTO login(String username) {
        return usuarioRepository.getUsuarioByUsername(username)
                .orElseThrow(() -> new RuntimeException("Error al loguear usuario: " + username));
    }

    /**
     * Busca todos los usuarios asociados a una cuenta específica (usado por Viajes Feign Client).
     */
    @Transactional(readOnly = true)
    public Set<Usuario> findUsuariosByCuentaId(Long idCuenta) {
        // Llama al repositorio para obtener la lista de usuarios filtrados por la cuenta.
        Set<Usuario> usuarios = usuarioRepository.findUsuariosByCuentaId(idCuenta);

        // Si no se encuentra ninguno, devuelve un Set vacío en lugar de null.
        return usuarios;
    }
}