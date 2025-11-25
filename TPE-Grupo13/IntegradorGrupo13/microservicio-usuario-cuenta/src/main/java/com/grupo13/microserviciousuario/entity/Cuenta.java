/**
 * 🏛️ Entidad JPA (Modelo de Dominio) para la tabla 'Cuenta'.
 *
 * Define la estructura de las cuentas de usuario, incluyendo el saldo, el estado
 * (ACTIVA/INACTIVA) y el tipo de cuenta (BÁSICA/PREMIUM). Es la entidad central
 * que gestiona el dinero.
 * * Aspectos clave:
 * 1. Mapeo @ManyToMany con Usuario, permitiendo que una cuenta esté asociada a
 * múltiples usuarios.
 * 2. Uso de @Enumerated(EnumType.STRING) para manejar el Estado y Tipo de Cuenta.
 * 3. Lógica @PrePersist para inicializar campos (fechaAlta, estado, saldo, tipoCuenta).
 * 4. Uso de @JsonIgnore en la relación 'usuarios' para evitar ciclos de serialización
 * infinitos al exponer la cuenta por API.
 */
package com.grupo13.microserviciousuario.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String idMercadoPago;

    @Column
    private BigDecimal saldo;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCuenta estado;

    @Column(nullable = false)
    private LocalDate fechaAlta;


    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta", nullable = false)
    private TipoCuenta tipoCuenta;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "cuenta_usuario",
            joinColumns = @JoinColumn(name = "id_cuenta"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private Set<Usuario> usuarios = new HashSet<>();
    @Column(name = "km_consumidos_mes")
    private double kmConsumidosMes; // Se usa solo si tipoCuenta es PREMIUM
    @Column(name = "fecha_renovacion_cupo")
    private LocalDate fechaRenovacionCupo; // Se usa solo si tipoCuenta es PREMIUM

    public void addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        usuario.getCuentas().add(this);
    }

    @PrePersist
    public void prePersist() {
        if (this.fechaAlta == null) this.fechaAlta = LocalDate.now();
        if (this.estado == null) this.estado = EstadoCuenta.ACTIVA;
        if (this.tipoCuenta == null) this.tipoCuenta = TipoCuenta.BASICA;
        if (this.saldo == null) this.saldo = BigDecimal.ZERO;
        this.kmConsumidosMes = 0.0;
    }

}

