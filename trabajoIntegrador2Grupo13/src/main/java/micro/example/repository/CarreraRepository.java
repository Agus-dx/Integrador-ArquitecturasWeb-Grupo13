package micro.example.repository;

import micro.example.dto.CarreraDTO;
import micro.example.modelo.Carrera;

import java.util.List;

public interface CarreraRepository {
    void insertarDesdeCSV(String rutaArchivo);
    List<CarreraDTO> getCarrerasConEstudiantes();
    Carrera findById(long id);
}
