package com.grupo13.integrador3.utils;

import com.grupo13.integrador3.model.Carrera;
import com.grupo13.integrador3.model.Estudiante;
import com.grupo13.integrador3.model.EstudianteCarrera;
import com.grupo13.integrador3.repository.CarreraRepository;
import com.grupo13.integrador3.repository.EstudianteCarreraRepository;
import com.grupo13.integrador3.repository.EstudianteRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.io.FileReader;
import java.util.ArrayList;

@Component // Indica que esta clase es un componente de Spring y debe ser gestionada por el contenedor.
public class CargarDatos implements CommandLineRunner {

    private final String rutaCsv="src/main/resources/csv/";

    // Inyección de dependencias de los Repositorios para interactuar con la base de datos.
    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private EstudianteCarreraRepository estudianteCarreraRepository;


    /**
     * Implementación del método 'run' de la interfaz CommandLineRunner.
     * Este método se ejecuta automáticamente una vez que la aplicación Spring ha arrancado.
     */
    @Override
    public void run(String... args) throws Exception {
        cargarDatos();
    }

    /**
     * Método principal que coordina la carga de todos los conjuntos de datos.
     * Llama secuencialmente a los métodos privados de carga.
     */
    public void cargarDatos() {
        cargarEstudiantes(rutaCsv+"estudiantes.csv");
        cargarCarreras(rutaCsv+"carreras.csv");
        cargarEstudianteCarrera(rutaCsv+"estudianteCarrera.csv");
    }

    /**
     * Carga los datos de estudiantes desde un archivo CSV.
     * Primero verifica si la tabla de estudiantes está vacía para evitar duplicados.
     */
    private void cargarEstudiantes(String ubicacion){
        try{
            // Verificación para evitar la doble carga.
            if (estudianteRepository.count() > 0) {
                System.out.println("Los estudiantes ya fueron cargados previamente");
                return;
            }
            ArrayList<Estudiante> estudiantes = new ArrayList<>();
            // Lee el archivo CSV, parsea los registros y crea objetos Estudiante.
            CSVParser registros = CSVFormat.DEFAULT.withHeader().parse(new FileReader(ubicacion));
            for(CSVRecord registro:registros){
                Estudiante estudiante=new Estudiante(
                        Long.parseLong(registro.get(0)),
                        registro.get(1),
                        registro.get(2),
                        Integer.parseInt(registro.get(3)),
                        registro.get(4),
                        registro.get(5),
                        Long.parseLong(registro.get(6))
                );
                estudiantes.add(estudiante);
            }
            if (estudiantes.isEmpty()){
                System.out.println("No se pudieron cargar los estudiantes");
            }else {
                // Guarda todos los nuevos estudiantes en la base de datos.
                estudianteRepository.saveAll(estudiantes);
                System.out.println("Estudiantes cargados correctamente");
            }

        }catch(Exception e){
            System.err.println("Error al cargar estudiantes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carga los datos de carreras desde un archivo CSV.
     * Primero verifica si la tabla de carreras está vacía.
     */
    private void cargarCarreras(String ubicacion){
        try {
            // Verificación para evitar duplicados.
            if (carreraRepository.count() > 0) {
                System.out.println("Las carreras ya fueron cargadas previamente");
                return;
            }
            ArrayList<Carrera> carreras = new ArrayList<>();
            // Lee el CSV, parsea y crea objetos Carrera.
            CSVParser registros = CSVFormat.DEFAULT.withHeader().parse(new FileReader(ubicacion));
            for(CSVRecord registro:registros){
                Carrera carrera = new Carrera(
                        registro.get(1),
                        Integer.parseInt(registro.get(2))
                );
                carreras.add(carrera);
            }
            if (carreras.isEmpty()){
                System.out.println("No se pudieron cargar las carreras");
            }else {
                // Guarda todas las nuevas carreras.
                carreraRepository.saveAll(carreras);
                System.out.println("Carreras cargadas correctamente");
            }
        }catch(Exception e){
            System.err.println("Error al cargar carreras: " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Carga los datos de matrícula (relaciones Estudiante-Carrera) desde un CSV.
     * Primero verifica si la tabla de matrículas está vacía.
     * Requiere que las carreras y estudiantes ya hayan sido cargados.
     */
    private void cargarEstudianteCarrera(String ubicacion){
        try{
            // Verificación para evitar la doble carga.
            if (estudianteCarreraRepository.count() > 0) {
                System.out.println("Las matriculas ya fueron cargadas previamente");
                return;
            }
            // Itera sobre los registros de matrícula.
            CSVParser registros = CSVFormat.DEFAULT.withHeader().parse(new FileReader(ubicacion));
            for(CSVRecord registro:registros){
                // Busca la Carrera y el Estudiante por su ID para establecer la relación.
                Carrera carrera = carreraRepository.findById(Long.parseLong(registro.get(2))).orElse(null);
                Estudiante estudiante = estudianteRepository.findById(Long.parseLong(registro.get(1))).orElse(null);

                // Solo crea la matrícula si ambos (carrera y estudiante) existen.
                if(carrera != null && estudiante != null){
                    EstudianteCarrera matricula = new EstudianteCarrera(
                            Integer.parseInt(registro.get("inscripcion")),
                            Integer.parseInt(registro.get("graduacion")),
                            Integer.parseInt(registro.get("antiguedad")),
                            estudiante,
                            carrera
                    );
                    // Guarda cada matrícula individualmente.
                    estudianteCarreraRepository.save(matricula);
                }
            }
        }catch(Exception e){
            System.err.println("Error al cargar estudiante-carrera: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
