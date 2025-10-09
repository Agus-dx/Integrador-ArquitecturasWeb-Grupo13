package micro.example.repository;

import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import micro.example.dto.CarreraDTO;
import micro.example.factory.JPAUtil;
import micro.example.modelo.Carrera;

import java.io.FileReader;
import java.util.List;

public class CarreraRepositoryImpl implements CarreraRepository {
    @Override
    public void insertarDesdeCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();
        try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
            String[] linea;
            reader.readNext();
            em.getTransaction().begin();
            while ((linea = reader.readNext()) != null) {
                Carrera carrera = new Carrera();
                carrera.setNombre(linea[1]);
                carrera.setDuracion(Integer.parseInt(linea[2]));
                em.persist(carrera);
            }
            em.getTransaction().commit();
        }catch (Exception e) {
            throw new RuntimeException("*** Error al cargar las carreras ***",e);
        }finally {
            em.close();
        }
    }
    @Override
    public List<CarreraDTO> getCarrerasWithEstudiantes() {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            String jpql = "SELECT new micro.example.dto.CarreraDTO(c.nombre, c.duracion, COUNT(e.id)) " +
                    "FROM Carrera c JOIN c.estudiantes e " +
                    "GROUP BY c.id, c.nombre, c.duracion " +
                    "ORDER BY COUNT(e.id) DESC";
            List<CarreraDTO> carreras = em.createQuery(jpql,CarreraDTO.class).getResultList();
            em.close();
            return carreras;
        } catch (Exception e) {
            throw new RuntimeException("*** Error al obtener las carreras con Estudiantes ***",e);
        }

    }
    @Override
    public Carrera findById(long id) {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            Carrera carrera = em.find(Carrera.class,id);
            em.close();
            return carrera;
        } catch (Exception e) {
            throw new RuntimeException("*** Error al buscar la carrera por ID ***",e);
        }
    }
}
