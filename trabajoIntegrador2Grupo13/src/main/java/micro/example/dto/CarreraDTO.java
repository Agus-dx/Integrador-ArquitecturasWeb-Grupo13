package micro.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarreraDTO {
    private String nombre;
    private int duracion;
    private long cantEstudiantes;
}
