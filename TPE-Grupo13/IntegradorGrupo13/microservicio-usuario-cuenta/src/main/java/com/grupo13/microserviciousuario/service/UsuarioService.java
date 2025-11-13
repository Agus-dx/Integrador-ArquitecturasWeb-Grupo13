package com.grupo13.microserviciousuario.service;

import com.grupo13.microserviciousuario.entity.Cuenta;
import com.grupo13.microserviciousuario.entity.EstadoCuenta;
import com.grupo13.microserviciousuario.entity.Usuario;
import com.grupo13.microserviciousuario.repository.CuentaRepository;
import com.grupo13.microserviciousuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public Usuario asignarCuenta(Long idUsuario, Long idCuenta) {
        // Busca y lanza excepción si no encuentra el usuario
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + idUsuario));

        // Busca y lanza excepción si no encuentra la cuenta
        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + idCuenta));

        usuario.addCuenta(cuenta); // Asumiendo que addCuenta existe
        return usuarioRepository.save(usuario);
    }

    /**
     * Cambia el estado de TODAS las cuentas de un usuario. Lanza RuntimeException si el usuario no existe.
     */
    @Transactional
    public void cambiarEstadoCuentas(Long idUsuario, EstadoCuenta nuevoEstado) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + idUsuario));

        for (Cuenta cuenta : usuario.getCuentas()) {
            cuenta.setEstado(nuevoEstado);
            cuentaRepository.save(cuenta);
        }
    }

    @Transactional(readOnly = true)
    public boolean cuentaAsociada(Long idUsuario, Long idCuenta) {
        if (idUsuario == null || idCuenta == null) {
            return false;
        }
        return usuarioRepository.existsCuentaInUsuario(idUsuario,idCuenta);
    }
}