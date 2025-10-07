package micro.example.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estudiante")
@Getter
@Setter
@NoArgsConstructor
public class Estudiante {

    @Id
    private long dni;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apellido", nullable = false)
    private String apellido;
    @Column(name = "edad", nullable = false)
    private int edad;
    @Column(name = "genero", nullable = false)
    private String genero;
    @Column(name = "ciudadOrigen", nullable = false)
    private String ciudadOrigen;
    @Column(name = "numeroLibreta", nullable = false, unique = true)
    private int numeroLibreta;
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EstudianteCarrera> listCarreras;

    public Estudiante(long dni, String nombre, String apellido, int edad, String genero, String ciudad, int nroLibreta) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.ciudadOrigen = ciudad;
        this.numeroLibreta = nroLibreta;
        this.listCarreras = new ArrayList<>();
    }
}

