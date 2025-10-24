package micro.example.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estudiantecarrera")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteCarrera {
    /**
     * Clave primaria compuesta de la entidad.
     * * @EmbeddedId: Indica que la clave primaria de esta entidad es un objeto embebido (la clase EstudianteCarreraPK).
     */
    @EmbeddedId
    private EstudianteCarreraPK id;

    /**
     * Entidad Carrera referenciada
     * * Representa el lado "muchos" de la relación con Carrera
     * * @ManyToOne: Relación muchos-a-uno
     * * @MapsId("idCarrera"): Mapea el campo 'idCarrera' de la clave compuesta (EstudianteCarreraPK)
     * a la columna 'id_carrera'
     * * @JoinColumn(name = "id_carrera", nullable = false): Define la clave foránea a la tabla Carrera
     */
    @ManyToOne (fetch=FetchType.LAZY)
    @MapsId("idCarrera")
    @JoinColumn(name = "id_carrera", nullable = false, insertable = false, updatable = false)
    private Carrera carrera;

    /**
     * Entidad Estudiante referenciada
     * * Representa el lado "muchos" de la relación con Estudiante
     * * @ManyToOne: Relación muchos-a-uno
     * * @MapsId("idEstudiante"): Mapea el campo 'idEstudiante' de la clave compuesta (EstudianteCarreraPK)
     * a la columna 'id_estudiante'
     * * @JoinColumn(name = "id_estudiante", nullable = false): Define la clave foránea a la tabla Estudiante
     */
    @ManyToOne (fetch=FetchType.LAZY)
    @MapsId("idEstudiante")
    @JoinColumn(name = "id_estudiante", nullable = false, insertable = false, updatable = false)
    private Estudiante estudiante;

    @Column(name = "inscripcion",nullable = false)
    private int inscripcion;
    @Column(name = "graduacion")
    private int graduacion;
    @Column(name = "antiguedad",nullable = false)
    private int antiguedad;
}
