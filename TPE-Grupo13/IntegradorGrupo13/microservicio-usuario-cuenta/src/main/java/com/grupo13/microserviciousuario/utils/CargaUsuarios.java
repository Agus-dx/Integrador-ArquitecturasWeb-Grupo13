/**
 * 🛠️ Componente de Carga de Datos Iniciales (Data Seeder).
 *
 * Implementa la interfaz CommandLineRunner, lo que garantiza que el método 'run'
 * se ejecute una vez que la aplicación Spring Boot ha arrancado completamente y
 * todas las dependencias (como Repositorios y PasswordEncoder) han sido inyectadas.
 * Su propósito es:
 * 1. Crear usuarios de ejemplo con diferentes Roles.
 * 2. Crear cuentas con distintos Tipos y Estados.
 * 3. Establecer las relaciones Many-to-Many entre usuarios y cuentas, incluyendo
 * casos de cuentas compartidas.
 * 4. Codificar las contraseñas usando PasswordEncoder antes de la persistencia.
 */
package com.grupo13.microserviciousuario.utils;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.grupo13.microserviciousuario.entity.Cuenta;
import com.grupo13.microserviciousuario.entity.EstadoCuenta;
import com.grupo13.microserviciousuario.entity.TipoCuenta;
import com.grupo13.microserviciousuario.entity.Usuario;
import com.grupo13.microserviciousuario.entity.Rol;
import com.grupo13.microserviciousuario.repository.CuentaRepository;
import com.grupo13.microserviciousuario.repository.UsuarioRepository;

@Component
public class CargaUsuarios implements CommandLineRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if(this.usuarioRepository.count() > 0) return;
        if(this.cuentaRepository.count() > 0) return;
        CargarDatosIniciales(usuarioRepository, cuentaRepository, passwordEncoder);
    }

    public static void CargarDatosIniciales(UsuarioRepository usuarioRepositoryy, CuentaRepository cuentaRepositoryy, PasswordEncoder passwordEncoder) {
        // Lógica para cargar datos iniciales en la base de datos
        // generame 5 usuarios y 5 cuentas con datos de ejemplo y asocialos entre si

        Cuenta cuenta1 = new Cuenta();
        cuenta1.setIdMercadoPago("MP00001");
        cuenta1.setSaldo(new BigDecimal("1000.00"));
        cuenta1.setEstado(EstadoCuenta.ACTIVA);
        cuenta1.setFechaAlta(LocalDate.now());
        cuenta1.setTipoCuenta(TipoCuenta.BASICA);
        cuenta1.setKmConsumidosMes(0);
        cuenta1.setFechaRenovacionCupo(null);

        Cuenta cuenta2 = new Cuenta();
        cuenta2.setIdMercadoPago("MP00002");
        cuenta2.setSaldo(new BigDecimal("2500.00"));
        cuenta2.setEstado(EstadoCuenta.ACTIVA);
        cuenta2.setFechaAlta(LocalDate.now());
        cuenta2.setTipoCuenta(TipoCuenta.PREMIUM);
        cuenta2.setKmConsumidosMes(150);
        cuenta2.setFechaRenovacionCupo(LocalDate.now().plusMonths(1));

        Cuenta cuenta3 = new Cuenta();
        cuenta3.setIdMercadoPago("MP00003");
        cuenta3.setSaldo(new BigDecimal("500.00"));
        cuenta3.setEstado(EstadoCuenta.SUSPENDIDA);
        cuenta3.setFechaAlta(LocalDate.now().minusMonths(2));
        cuenta3.setTipoCuenta(TipoCuenta.BASICA);
        cuenta3.setKmConsumidosMes(0);
        cuenta3.setFechaRenovacionCupo(null);

        Cuenta cuenta4 = new Cuenta();
        cuenta4.setIdMercadoPago("MP00004");
        cuenta4.setSaldo(new BigDecimal("3000.00"));
        cuenta4.setEstado(EstadoCuenta.ACTIVA);
        cuenta4.setFechaAlta(LocalDate.now().minusMonths(1));
        cuenta4.setTipoCuenta(TipoCuenta.PREMIUM);
        cuenta4.setKmConsumidosMes(200);
        cuenta4.setFechaRenovacionCupo(LocalDate.now().plusMonths(1));

        Cuenta cuenta5 = new Cuenta();
        cuenta5.setIdMercadoPago("MP00005");
        cuenta5.setSaldo(new BigDecimal("750.00"));
        cuenta5.setEstado(EstadoCuenta.ACTIVA);
        cuenta5.setFechaAlta(LocalDate.now());
        cuenta5.setTipoCuenta(TipoCuenta.BASICA);
        cuenta5.setKmConsumidosMes(0);
        cuenta5.setFechaRenovacionCupo(null);

        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Agustin");
        usuario1.setApellido("Morales");
        usuario1.setEmail("agus@gmail.com");
        usuario1.setPassword(passwordEncoder.encode("agus"));
        usuario1.setRol(Rol.ADMIN);
        usuario1.addCuenta(cuenta1);
        usuario1.addCuenta(cuenta2);

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Joaquin");
        usuario2.setApellido("Morales");
        usuario2.setEmail("joaco@gmail.com");
        usuario2.setPassword(passwordEncoder.encode("joaco"));
        usuario2.setRol(Rol.USUARIO);
        usuario2.addCuenta(cuenta3);

        Usuario usuario3 = new Usuario();
        usuario3.setNombre("Julieta");
        usuario3.setApellido("Simos");
        usuario3.setEmail("juli@gmail.com");
        usuario3.setPassword(passwordEncoder.encode("juli"));
        usuario3.setRol(Rol.USUARIO);
        usuario3.addCuenta(cuenta4);

        Usuario usuario4 = new Usuario();
        usuario4.setNombre("nombre4");
        usuario4.setApellido("apellido4");
        usuario4.setEmail("usuario4@gmail.com");
        usuario4.setPassword(passwordEncoder.encode("1234"));
        usuario4.setRol(Rol.USUARIO);
        usuario4.addCuenta(cuenta5);

        Usuario usuario5 = new Usuario();
        usuario5.setNombre("nombre5");
        usuario5.setApellido("apellido5");
        usuario5.setEmail("usuario5@gmail.com");
        usuario5.setPassword(passwordEncoder.encode("1234"));
        usuario5.setRol(Rol.USUARIO);

        cuenta1.addUsuario(usuario1);
        cuenta2.addUsuario(usuario1);
        cuenta3.addUsuario(usuario2);
        cuenta4.addUsuario(usuario3);
        cuenta5.addUsuario(usuario4);

        // Agrego más cuentas y usuarios compartidos para tener datos de reportes

        Cuenta cuenta6 = new Cuenta();
        cuenta6.setIdMercadoPago("MP00006");
        cuenta6.setSaldo(new BigDecimal("1200.00"));
        cuenta6.setEstado(EstadoCuenta.ACTIVA);
        cuenta6.setFechaAlta(LocalDate.now().minusDays(10));
        cuenta6.setTipoCuenta(TipoCuenta.BASICA);
        cuenta6.setKmConsumidosMes(20);
        cuenta6.setFechaRenovacionCupo(LocalDate.now().plusMonths(2));

        Cuenta cuenta7 = new Cuenta();
        cuenta7.setIdMercadoPago("MP00007");
        cuenta7.setSaldo(new BigDecimal("400.00"));
        cuenta7.setEstado(EstadoCuenta.ACTIVA);
        cuenta7.setFechaAlta(LocalDate.now().minusDays(5));
        cuenta7.setTipoCuenta(TipoCuenta.BASICA);
        cuenta7.setKmConsumidosMes(0);
        cuenta7.setFechaRenovacionCupo(null);

        Usuario usuario6 = new Usuario();
        usuario6.setNombre("nombre6");
        usuario6.setApellido("apellido6");
        usuario6.setEmail("usuario6@gmail.com");
        usuario6.setPassword(passwordEncoder.encode("1234"));
        usuario6.setRol(Rol.USUARIO);
        usuario6.addCuenta(cuenta2); // comparte cuenta2 con Juan

        Usuario usuario7 = new Usuario();
        usuario7.setNombre("nombre7");
        usuario7.setApellido("apellido7");
        usuario7.setEmail("usuario7@gmail.com");
        usuario7.setPassword(passwordEncoder.encode("1234"));
        usuario7.setRol(Rol.USUARIO);
        usuario7.addCuenta(cuenta4); // comparte cuenta4 con Carlos

        Usuario usuario8 = new Usuario();
        usuario8.setNombre("nombre8");
        usuario8.setApellido("apellido8");
        usuario8.setEmail("usuario8@gmail.com");
        usuario8.setPassword(passwordEncoder.encode("password123"));
        usuario8.setRol(Rol.USUARIO);
        usuario8.addCuenta(cuenta3); // comparte cuenta3 con Maria
        usuario8.addCuenta(cuenta6); // nueva cuenta compartida

        Usuario usuario9 = new Usuario();
        usuario9.setNombre("nombre9");
        usuario9.setApellido("apellido8");
        usuario9.setEmail("usuario9@gmail.com");
        usuario9.setPassword(passwordEncoder.encode("password123"));
        usuario9.setRol(Rol.USUARIO);
        usuario9.addCuenta(cuenta1); // comparte cuenta1 con Juan
        usuario9.addCuenta(cuenta7);

        // Asociaciones bidireccionales
        cuenta2.addUsuario(usuario6);
        cuenta4.addUsuario(usuario7);
        cuenta3.addUsuario(usuario8);
        cuenta6.addUsuario(usuario8);
        cuenta1.addUsuario(usuario9);
        cuenta7.addUsuario(usuario9);

        // Compartir cuenta5 también con Maria (usuario2)
        cuenta5.addUsuario(usuario2);
        usuario2.addCuenta(cuenta5);

        // guardar en la base de datos (guardar usuarios y cuentas nuevas)
        usuarioRepositoryy.save(usuario1);
        usuarioRepositoryy.save(usuario2);
        usuarioRepositoryy.save(usuario3);
        usuarioRepositoryy.save(usuario4);
        usuarioRepositoryy.save(usuario5);
        usuarioRepositoryy.save(usuario6);
        usuarioRepositoryy.save(usuario7);
        usuarioRepositoryy.save(usuario8);
        usuarioRepositoryy.save(usuario9);

        cuentaRepositoryy.save(cuenta1);
        cuentaRepositoryy.save(cuenta2);
        cuentaRepositoryy.save(cuenta3);
        cuentaRepositoryy.save(cuenta4);
        cuentaRepositoryy.save(cuenta5);
        cuentaRepositoryy.save(cuenta6);
        cuentaRepositoryy.save(cuenta7);

    }

}

