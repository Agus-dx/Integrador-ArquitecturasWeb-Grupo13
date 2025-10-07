package micro.example.repository;

import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import micro.example.dto.ReporteDTO;
import micro.example.factory.JPAUtil;
import micro.example.modelo.*;

import java.io.FileReader;
import java.util.List;

public class EstudianteCarreraRepositoryImpl implements EstudianteCarreraRepository {
    @Override
    public void insertarDesdeCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();
        try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
            String[] linea;
            reader.readNext();
            em.getTransaction().begin();
            while ((linea = reader.readNext()) != null) {

                // 1. Buscar Estudiante
                Estudiante e = em.find(Estudiante.class, Long.parseLong(linea[1]));

                // 2. Buscar Carrera
                Carrera c = em.find(Carrera.class, Long.parseLong(linea[2]));

                // 3. VALIDACIÓN: Asegurarse de que ambos existen
                if (e == null) {
                    System.err.println("ADVERTENCIA: Estudiante con ID (DNI) " + linea[1] + " no encontrado. Saltando inscripción.");
                    continue; // Ir a la siguiente línea del CSV
                }
                if (c == null) {
                    System.err.println("ADVERTENCIA: Carrera con ID " + linea[2] + " no encontrada. Saltando inscripción.");
                    continue; // Ir a la siguiente línea del CSV
                }

                // Si ambos existen, crear la entidad de relación
                EstudianteCarrera ec = new EstudianteCarrera();
                ec.setEstudiante(e);
                ec.setCarrera(c);
                ec.setInscripcion(Integer.parseInt(linea[3]));
                ec.setGraduacion(Integer.parseInt(linea[4]));
                ec.setAntiguedad(Integer.parseInt(linea[5]));

                em.persist(ec);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("*** Error al cargar los EstudianteCarrera ***",e);
        } finally {
            em.close();
        }
    }
    @Override
    public void matricularEstudiante(EstudianteCarrera estudianteCarrera) {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            em.persist(estudianteCarrera);
            em.getTransaction().commit();
            em.close();
        }catch (Exception e){
            throw new RuntimeException("*** Error al matricular el estudiante ***",e);
        }
    }
    @Override
    public List<ReporteDTO> getReportes() {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            String jpql = "SELECT new micro.example.dto.ReporteDTO(" +
                    "ec.graduacion,c.nombre,COUNT(ec)," +
                    "(SELECT COUNT(ec2) FROM EstudianteCarrera ec2 " +
                    "WHERE ec2.carrera.nombre = c.nombre) ) " +
                    "FROM EstudianteCarrera ec JOIN ec.carrera c " +
                    "WHERE ec.graduacion != 0 " +
                    "GROUP BY ec.graduacion,c.nombre " +
                    "ORDER BY c.nombre,ec.graduacion";
            List<ReporteDTO> reportes = em.createQuery(jpql,ReporteDTO.class).getResultList();
            em.close();
            return reportes;
        } catch (Exception e) {
            throw new RuntimeException("*** Error al generar reporte ***",e);
        }
    }
}
