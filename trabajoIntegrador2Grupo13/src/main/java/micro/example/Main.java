package micro.example;


import micro.example.dto.CarreraDTO;
import micro.example.dto.EstudianteDTO;
import micro.example.dto.ReporteDTO;
import micro.example.modelo.Carrera;
import micro.example.modelo.Estudiante;
import micro.example.modelo.EstudianteCarrera;
import micro.example.repository.CarreraRepositoryImpl;
import micro.example.repository.EstudianteCarreraRepositoryImpl;
import micro.example.repository.EstudianteRepositoryImpl;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EstudianteRepositoryImpl er = new EstudianteRepositoryImpl();
        er.insertarDesdeCSV("src/main/resources/estudiantes.csv");
        CarreraRepositoryImpl cr = new CarreraRepositoryImpl();
        cr.insertarDesdeCSV("src/main/resources/carreras.csv");
        EstudianteCarreraRepositoryImpl ecr = new EstudianteCarreraRepositoryImpl();
        ecr.insertarDesdeCSV("src/main/resources/estudianteCarrera.csv");

        // Menu
        boolean temp = true;
        while (temp) {

            System.out.println("1. Dar de alta un estudiante");
            System.out.println("2. Matricular un estudiante en una carrera");
            System.out.println("3. Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple");
            System.out.println("4. Recuperar un estudiante, en base a su número de libreta universitaria");
            System.out.println("5. Recuperar todos los estudiantes, en base a su género");
            System.out.println("6. Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos");
            System.out.println("7. Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia");
            System.out.println("8. Generar reporte de las carreras");
            System.out.println("9. Salir");
            System.out.print("Ingrese una opción: ");
            Scanner scanner = new Scanner(System.in);
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:{
                    // Dar de alta un estudiante
                    System.out.println("Ingrese los datos del estudiante:");
                    System.out.println("Ingrese Nombre: ");
                    String nombre = scanner.next();
                    System.out.println("Ingrese Apellido: ");
                    String apellido = scanner.next();
                    System.out.println("Ingrese DNI: ");
                    long dni = scanner.nextInt();
                    System.out.println("Ingrese Edad: ");
                    int edad = scanner.nextInt();
                    System.out.println("Ingrese Genero: ");
                    String genero = scanner.next();
                    System.out.println("Ingrese Ciudad de origen: ");
                    String ciudadOrigen = scanner.next();
                    System.out.println("Ingrese Numero Libreta: ");
                    int numeroLibreta = scanner.nextInt();
                    Estudiante estudiante = new Estudiante(dni, nombre, apellido, edad, genero, ciudadOrigen, numeroLibreta);
                    er.addEstudiante(estudiante);
                    System.out.println("Estudiante agregado");
                    System.out.println("\n");
                    break;
                }
                case 2: {
                    // Matricular un estudiante en una carrera
                    System.out.println("Ingrese el número de DNI del estudiante a matricular: ");
                    int dni = scanner.nextInt();
                    System.out.println("Ingrese el ID de la carrera: ");
                    long idCarrera = scanner.nextInt();
                    Estudiante estudiante = er.findById(dni);
                    Carrera carrera = cr.findById(idCarrera);
                    // Validar existencia
                    if (estudiante == null || carrera == null) {
                        System.out.println("* Estudiante o carrera no encontrada *");
                        System.out.println();
                        break;
                    }
                    // Crear la relación estudiante-carrera
                    EstudianteCarrera estudianteCarrera = new EstudianteCarrera();
                    estudianteCarrera.setInscripcion(2025); // año actual
                    estudianteCarrera.setGraduacion(0);     // aún no graduado
                    estudianteCarrera.setAntiguedad(0);
                    estudianteCarrera.setEstudiante(estudiante);
                    estudianteCarrera.setCarrera(carrera);
                    try {
                        ecr.matricularEstudiante(estudianteCarrera);
                        System.out.println("Estudiante matriculado en la carrera: " + carrera.getNombre());
                    } catch (Exception ex) {
                        System.out.println("El estudiante ya está inscrito en la carrera: "
                                + carrera.getNombre() + " (" + ex.getMessage() + ")");
                    }
                    System.out.println();
                    break;
                }
                case 3: {
                    // Recuperar todos los estudiantes, ordenados según un criterio
                    System.out.println("Ingrese el criterio de ordenamiento (nombre, apellido, ciudadOrigen, ...): ");
                    String criterio = scanner.next().toLowerCase(); // convertir a minúsculas para evitar errores
                    try {
                        List<EstudianteDTO> estudiantes = er.getEstudiantesSorted(criterio);
                        if (estudiantes == null || estudiantes.isEmpty()) {
                            System.out.println("No se encontraron estudiantes en la base de datos.");
                        } else {
                            System.out.println("Estudiantes ordenados por " + criterio + ":");
                            for (EstudianteDTO est : estudiantes) {
                                System.out.println(est);
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("*** Criterio de ordenamiento no válido: " + criterio + " ***");
                    } catch (Exception e) {
                        // Cualquier otro error inesperado
                        System.out.println("*** Error al recuperar estudiantes ***");
                    }
                    System.out.println();
                    break;
                }
                case 4: {
                    // Recuperar un estudiante en base a su número de libreta universitaria
                    System.out.println("Ingrese el número de libreta universitaria del estudiante a buscar: ");
                    int nroLibreta = scanner.nextInt();
                    try {
                        EstudianteDTO estudiante = er.getEstudianteByLU(nroLibreta);
                        if (estudiante != null) {
                            System.out.println("Estudiante: ");
                            System.out.println(estudiante);
                        } else {
                            System.out.println("No se encontró ningún estudiante con el número de libreta: " + nroLibreta);
                        }
                    } catch (Exception e) {
                        System.out.println("*** Error al buscar estudiante ***" + e.getMessage());
                    }
                    System.out.println();
                    break;
                }
                case 5: {
                    // Recuperar todos los estudiantes en base a su género
                    System.out.println("Ingrese género: ");
                    String genero = scanner.next().toLowerCase();
                    try {
                        List<EstudianteDTO> estudiantesGenero = er.getEstudiantesByGenero(genero);
                        if (estudiantesGenero == null || estudiantesGenero.isEmpty()) {
                            System.out.println("No se encontraron estudiantes con género: " + genero);
                        } else {
                            System.out.println("Estudiantes de género " + genero + ":");
                            for (EstudianteDTO estGenero : estudiantesGenero) {
                                System.out.println(estGenero);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("*** Error al buscar estudiantes por género ***");
                    }
                    System.out.println();
                    break;
                }
                case 6: {
                    // Recuperar las carreras con estudiantes inscriptos, ordenadas por cantidad de inscriptos
                    try {
                        List<CarreraDTO> carreras = cr.getCarrerasConEstudiantes();
                        if (carreras == null || carreras.isEmpty()) {
                            System.out.println("No hay carreras con estudiantes inscriptos.");
                        } else {
                            System.out.println("Carreras con estudiantes inscriptos (ordenadas por cantidad):");
                            for (CarreraDTO carrera : carreras) {
                                System.out.println(carrera);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("*** Error al recuperar las carreras con estudiantes ***");
                    }
                    System.out.println();
                    break;
                }
                case 7: {
                    // Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia
                    // Consumir el salto de línea pendiente del scanner.nextInt()
                    scanner.nextLine();
                    System.out.println("--- Búsqueda de Estudiantes por Carrera y Ciudad ---");
                    System.out.println("Ingrese el nombre de la carrera: ");
                    String nombreCarrera = scanner.nextLine().trim();
                    System.out.println("Ingrese la ciudad de residencia: ");
                    String ciudadResidencia = scanner.nextLine().trim();
                    if (nombreCarrera.isEmpty() || ciudadResidencia.isEmpty()) {
                        System.out.println("*** Error: La carrera y/o la ciudad no pueden estar vacías ***");
                        break;
                    }
                    try {
                        List<EstudianteDTO> estudiantesCarrera = er.getEstudiantesByCarreraAndCiudad(nombreCarrera,
                                ciudadResidencia);
                        if (estudiantesCarrera != null && !estudiantesCarrera.isEmpty()) {
                            System.out.println("Resultados para: Carrera '" + nombreCarrera + "' y Ciudad '"
                                    + ciudadResidencia + "':");
                            System.out.println("--------------------------------------------------");
                            for (EstudianteDTO estCarrera : estudiantesCarrera) {
                                System.out.println(estCarrera);
                            }
                        } else {
                            System.out.println("No se encontraron estudiantes que cumplan con ambos criterios.");
                        }
                    } catch (Exception e) {
                        System.out.println("*** Error al consultar los estudiantes: " + e.getMessage() + " ***");
                    }

                    System.out.println("\n");
                    break;
                }
                case 8: {
                    // Generar reporte de carreras
                    System.out.println("--- Generando Reporte de Carreras ---");
                    try {
                        List<ReporteDTO> reportes = ecr.getReportes();
                        if (reportes != null && !reportes.isEmpty()) {
                            System.out.println("Reporte de Carreras (Inscripciones por Carrera y Ciudad):");
                            System.out.println("---------------------------------------------------------");
                            for (ReporteDTO reporte : reportes) {
                                System.out.println(reporte);
                            }
                        } else {
                            System.out.println("No se encontraron datos para generar el reporte de carreras.");
                        }
                    } catch (Exception e) {
                        System.out.println("*** Error al generar el reporte de carreras: " + e.getMessage() + " ***");
                    }
                    System.out.println("\n");
                    break;
                }
                case 9:{
                    scanner.close();
                    System.out.println("Finalizando programa...");
                    temp = false;
                    break;
                }
                default: {
                    System.out.println("Opción no válida");
                }
            }

        }
    }
}