package micro.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EstudianteDTO {
    private long dni;
    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private String ciudad;
    private int nroLibreta;
}
