package com.grupo13.microserviciousuario.utils;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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

    @Override
    public void run(String... args) throws Exception {
        CargarDatosIniciales(usuarioRepository, cuentaRepository);
    }

    public static void CargarDatosIniciales(UsuarioRepository usuarioRepositoryy, CuentaRepository cuentaRepositoryy) {

        Cuenta cuenta1 = new Cuenta();
        cuenta1.setIdMercadoPago("MP54321");
        cuenta1.setSaldo(new BigDecimal("500.00"));
        cuenta1.setEstado(EstadoCuenta.SUSPENDIDA);
        cuenta1.setFechaAlta(LocalDate.now().minusMonths(2));
        cuenta1.setTipoCuenta(TipoCuenta.BASICA);
        cuenta1.setKmConsumidosMes(0);
        cuenta1.setFechaRenovacionCupo(null);

        Cuenta cuenta2 = new Cuenta();
        cuenta2.setIdMercadoPago("MP98765");
        cuenta2.setSaldo(new BigDecimal("3000.00"));
        cuenta2.setEstado(EstadoCuenta.ACTIVA);
        cuenta2.setFechaAlta(LocalDate.now().minusMonths(1));
        cuenta2.setTipoCuenta(TipoCuenta.PREMIUM);
        cuenta2.setKmConsumidosMes(200);
        cuenta2.setFechaRenovacionCupo(LocalDate.now().plusMonths(1));

        Cuenta cuenta3 = new Cuenta();
        cuenta3.setIdMercadoPago("MP11223");
        cuenta3.setSaldo(new BigDecimal("750.00"));
        cuenta3.setEstado(EstadoCuenta.ACTIVA);
        cuenta3.setFechaAlta(LocalDate.now());
        cuenta3.setTipoCuenta(TipoCuenta.BASICA);
        cuenta3.setKmConsumidosMes(0);
        cuenta3.setFechaRenovacionCupo(null);

        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Juan");
        usuario1.setApellido("Perez");
        usuario1.setRol(Rol.ADMIN);
        usuario1.addCuenta(cuenta1);
        usuario1.addCuenta(cuenta2);

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Maria");
        usuario2.setApellido("Gomez");
        usuario2.setRol(Rol.USUARIO);
        usuario2.addCuenta(cuenta1);

        Usuario usuario3 = new Usuario();
        usuario3.setNombre("Carlos");
        usuario3.setApellido("Lopez");
        usuario3.setRol(Rol.USUARIO);
        usuario3.addCuenta(cuenta2);

        Usuario usuario4 = new Usuario();
        usuario4.setNombre("Ana");
        usuario4.setApellido("Martinez");
        usuario4.setRol(Rol.USUARIO);
        usuario4.addCuenta(cuenta3);

        Usuario usuario5 = new Usuario();
        usuario5.setNombre("Luis");
        usuario5.setApellido("Rodriguez");
        usuario5.setRol(Rol.USUARIO);

        cuenta1.addUsuario(usuario1);
        cuenta2.addUsuario(usuario1);
        cuenta1.addUsuario(usuario2);
        cuenta2.addUsuario(usuario3);
        cuenta3.addUsuario(usuario4);

        // guarrdar en la base de datos
        UsuarioRepository usuarioRepository = usuarioRepositoryy;
        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);
        usuarioRepository.save(usuario3);
        usuarioRepository.save(usuario4);
        usuarioRepository.save(usuario5);
        CuentaRepository cuentaRepository = cuentaRepositoryy;
        cuentaRepository.save(cuenta1);
        cuentaRepository.save(cuenta2);
        cuentaRepository.save(cuenta1);
        cuentaRepository.save(cuenta2);
        cuentaRepository.save(cuenta3);

    }

}
