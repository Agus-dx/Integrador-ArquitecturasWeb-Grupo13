package micro.example.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EstudianteCarreraPK implements Serializable {

    /**
     * Clave foránea que referencia el ID de la Carrera (Primary Key de Carrera).
     * Se mapeará a la columna 'id_carrera' en la tabla EstudianteCarrera.
     * * Es el segundo componente de la clave compuesta.
     */
    @Column(name = "id_carrera")
    private Long idCarrera;

    /**
     * Clave foránea que referencia el DNI del Estudiante (Primary Key de Estudiante).
     * Se mapeará a la columna 'id_estudiante' en la tabla EstudianteCarrera.
     * * Es el primer componente de la clave compuesta.
     */
    @Column(name = "id_estudiante")
    private Long idEstudiante;


}
