/**
 * 🧑‍💻 Controlador REST (API Layer) para la gestión de Usuarios y Cuentas.
 *
 * Expone los endpoints principales para la gestión de la entidad Usuario (CRUD)
 * y maneja las complejas relaciones con la entidad Cuenta. Es vital para:
 * 1. La consulta de usuarios y la obtención de las cuentas asociadas.
 * 2. La verificación de la asociación usuario-cuenta (endpoint usado por otros microservicios).
 * 3. La asignación de cuentas a usuarios y la manipulación del estado de esas cuentas.
 * Utiliza códigos de estado HTTP estándar (200, 201, 204, 404, 400).
 */
package com.grupo13.microserviciousuario.controllers;

import com.grupo13.microserviciousuario.dtos.LoginDTO;
import com.grupo13.microserviciousuario.entity.Cuenta;
import com.grupo13.microserviciousuario.entity.Usuario;
import com.grupo13.microserviciousuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    @GetMapping("/username")// LOGIN
    public ResponseEntity<LoginDTO> getUsuarioByUsername(@RequestParam String username) {
        try {
            return ResponseEntity.ok(usuarioService.login(username));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{idUsuario}/cuenta-asociada/{idCuenta}")
    public ResponseEntity<Boolean> verificarCuentaAsociada(
            @PathVariable Long idUsuario,
            @PathVariable Long idCuenta) {

        // El service ejecuta la lógica en la BD
        boolean esValido = usuarioService.cuentaAsociada(idUsuario, idCuenta);

        // Retorna 200 OK con el booleano en el cuerpo
        return ResponseEntity.ok(esValido);
    }

    @GetMapping("/tipo/{rol}")
    public ResponseEntity<Set<Long>> getUsuariosByRol(@PathVariable String rol) {
        try {
            Set<Long> usuarios = usuarioService.getUsuarioByRol(rol);
            return ResponseEntity.ok(usuarios);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
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
            // Actualizar otros campos necesarios...
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
        Optional<Usuario> o = usuarioService.asignarCuenta(idUsuario, idCuenta);
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Método para obtener todos los usuarios asociados a una cuenta
    @GetMapping("/cuentas/{idCuenta}/usuarios")
    public ResponseEntity<?> getUsuariosByCuenta(@PathVariable Long idCuenta) {
        // Lógica de service: buscar todos los usuarios que tengan idCuenta asociado
        Set<Usuario> usuarios = usuarioService.findUsuariosByCuentaId(idCuenta);

        return ResponseEntity.ok(usuarios);
    }
}