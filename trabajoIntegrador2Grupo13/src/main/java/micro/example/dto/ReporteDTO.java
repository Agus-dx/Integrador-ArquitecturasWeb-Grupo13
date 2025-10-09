package micro.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReporteDTO {
    private int fechaEgreso;
    private String nombreCarrera;
    private long cantEgresados;
    private long cantInscriptos;
}
