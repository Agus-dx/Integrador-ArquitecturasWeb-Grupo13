package com.grupo13.microserviciousuario.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column(unique = true)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    @ManyToMany(mappedBy = "usuarios", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Cuenta> cuentas = new HashSet<>();

    @Column()
    private Double latitud;

    @Column()
    private Double longitud;

    /**
     * Metodo para añadir una cuenta al conjunto de cuentas del usuario.
     * @param cuenta La cuenta a asociar.
     */
    public void addCuenta(Cuenta cuenta) {
        if (this.cuentas == null) {
            this.cuentas = new HashSet<>();
        }
        this.cuentas.add(cuenta);
        cuenta.getUsuarios().add(this); // Sincronizar el otro lado de la relación
    }

    /**
     * Metodo para desasociar una cuenta del usuario.
     * @param cuenta La cuenta a remover.
     */
    public void removeCuenta(Cuenta cuenta) {
        if (this.cuentas != null) {
            this.cuentas.remove(cuenta);
            cuenta.getUsuarios().remove(this); // Sincronizar el otro lado de la relación
        }
    }
}