package com.grupo13.microserviciousuario.controllers;

import com.grupo13.microserviciousuario.entity.Cuenta;
import com.grupo13.microserviciousuario.entity.EstadoCuenta;
import com.grupo13.microserviciousuario.entity.Usuario;
import com.grupo13.microserviciousuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll(){
        List<Usuario> usuarios = usuarioService.findAll();
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cuentas/{id}")
    public ResponseEntity<Set<Cuenta>> obtenerCuentasUsuario(@PathVariable Long id) {
        try {
            Set<Cuenta> cuentas = usuarioService.getCuentasByUsuario(id);
            return ResponseEntity.ok(cuentas);
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{idUsuario}/cuenta-asociada/{idCuenta}")
    public ResponseEntity<Boolean> verificarCuentaAsociada(
            @PathVariable Long idUsuario,
            @PathVariable Long idCuenta) {
        boolean esValido = usuarioService.cuentaAsociada(idUsuario, idCuenta);
        return ResponseEntity.ok(esValido);
    }

    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@RequestBody Usuario usuario, @PathVariable Long id) {
        Usuario usuarioActual = usuarioService.findById(id);
        if (usuarioActual != null) {
            usuarioActual.setNombre(usuario.getNombre());
            usuarioActual.setApellido(usuario.getApellido());
            return ResponseEntity.ok(usuarioService.save(usuarioActual));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (usuarioService.findById(id) != null) {
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{idUsuario}/asignar-cuenta/{idCuenta}")
    public ResponseEntity<?> asignarCuenta(@PathVariable Long idUsuario, @PathVariable Long idCuenta) {
        try {
            Usuario usuario = usuarioService.asignarCuenta(idUsuario, idCuenta);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("no encontrado")) {
                return ResponseEntity.notFound().build();
            }
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{idUsuario}/cambiar-estado-cuentas")
    public ResponseEntity<Void> cambiarEstadoCuentas(@PathVariable Long idUsuario, @RequestParam EstadoCuenta nuevoEstado) {
        usuarioService.cambiarEstadoCuentas(idUsuario, nuevoEstado);
        return ResponseEntity.noContent().build();
    }
}