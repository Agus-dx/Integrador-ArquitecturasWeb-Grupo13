package micro.example.repository;

import micro.example.dto.ReporteDTO;
import micro.example.modelo.EstudianteCarrera;

import java.util.List;

public interface EstudianteCarreraRepository {
    void insertarDesdeCSV(String rutaArchivo);
    void matricularEstudiante(EstudianteCarrera estudianteCarrera);
    List<ReporteDTO> getReportes();
}
