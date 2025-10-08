package micro.example.repository;

import micro.example.dto.EstudianteDTO;
import micro.example.modelo.Estudiante;

import java.util.List;

public interface EstudianteRepository {
    void insertarDesdeCSV(String rutaArchivo);
    void addEstudiante(Estudiante estudiantes);
    List<EstudianteDTO> getEstudiantesSorted(String campo);
    List<EstudianteDTO> getEstudiantesByCarreraAndCiudad(String carrera,String ciudad);
    EstudianteDTO getEstudianteByLU(int nroLibreta);
    List<EstudianteDTO> getEstudiantesByGenero(String genero);
    Estudiante findById(long id);


}
