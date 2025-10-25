package micro.example.repository;

import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import micro.example.dto.EstudianteDTO;
import micro.example.enums.CampoOrdenamientoEstudiante;
import micro.example.factory.JPAUtil;
import micro.example.modelo.Estudiante;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EstudianteRepositoryImpl implements EstudianteRepository {
    private static EstudianteRepositoryImpl instance;

    private EstudianteRepositoryImpl() {
    }

    public static EstudianteRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new EstudianteRepositoryImpl();
        }
        return  instance;
    }

    @Override
    public void insertarDesdeCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();
        try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
            String[] linea;
            reader.readNext();
            em.getTransaction().begin();
            while ((linea = reader.readNext()) != null) {
                Estudiante e = new Estudiante();
                e.setDni(Long.parseLong(linea[0]));
                e.setNombre(linea[1]);
                e.setApellido(linea[2]);
                e.setEdad(Integer.parseInt(linea[3]));
                e.setGenero(linea[4]);
                e.setCiudadOrigen(linea[5]);
                e.setNumeroLibreta(Integer.parseInt(linea[6]));

                em.persist(e);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("*** Error al cargar los estudiantes ***", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void addEstudiante(Estudiante estudiante) {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            em.persist(estudiante);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            throw new RuntimeException("*** Error al cargar el estudiante ***", e);
        }
    }

    @Override
    public List<EstudianteDTO> getEstudiantesSorted(String campo) {
        try {
            CampoOrdenamientoEstudiante campoOrdenamiento = CampoOrdenamientoEstudiante.from(campo);
            String nombreCampo = campoOrdenamiento.getCampoEntidad();

            EntityManager em = JPAUtil.getEntityManager();
            String jpql = "SELECT new micro.example.dto.EstudianteDTO(" +
                    "e.dni, e.nombre, e.apellido, e.edad, e.genero, " +
                    "e.ciudadOrigen, e.numeroLibreta) " +
                    "FROM Estudiante e ORDER BY LOWER(e." + nombreCampo + ")";

            List<EstudianteDTO> estudiantes = em.createQuery(jpql, EstudianteDTO.class)
                    .getResultList();
            em.close();
            return estudiantes;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("*** Error al obtener estudiantes ordenados por " + campo + " ***", e);
        }
    }

    @Override
    public EstudianteDTO getEstudianteByLU(int numeroLibreta) {
        EstudianteDTO estudiante = null;
        try {
            EntityManager em = JPAUtil.getEntityManager();
            String jpql = "SELECT new micro.example.dto.EstudianteDTO(" +
                    "e.dni, e.nombre, e.apellido, e.edad, e.genero, " +
                    "e.ciudadOrigen, e.numeroLibreta) " +
                    "FROM Estudiante e WHERE e.numeroLibreta = :numeroLibreta";
            estudiante = em.createQuery(jpql, EstudianteDTO.class)
                    .setParameter("numeroLibreta", numeroLibreta)
                    .getSingleResult();
            em.close();
        } catch (NoResultException e) {
            throw new RuntimeException("*** No existe estudiante con número de libreta: "
                    + numeroLibreta + " ***", e);
        } catch (Exception e) {
            throw new RuntimeException("*** Error al obtener estudiante con número de libreta: "
                    + numeroLibreta + " ***", e);
        }
        return estudiante;
    }

    @Override
    public List<EstudianteDTO> getEstudiantesByGenero(String genero) {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            String jpql = "SELECT new micro.example.dto.EstudianteDTO(" +
                    "e.dni,e.nombre,e.apellido,e.edad,e.genero," +
                    "e.ciudadOrigen,e.numeroLibreta) " +
                    "FROM Estudiante e " +
                    "WHERE LOWER(e.genero) = LOWER(:genero)";
            List<EstudianteDTO> estudiantes = em.createQuery(jpql, EstudianteDTO.class).setParameter("genero", genero)
                    .getResultList();
            em.close();
            return estudiantes;
        } catch (Exception e) {
            throw new RuntimeException("*** Error al obtener estudiantes por genero " +
                    genero + " *** ", e);
        }
    }

    @Override
    public List<EstudianteDTO> getEstudiantesByCarreraAndCiudad(String carrera, String ciudadOrigen) {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            String jpql = "SELECT new micro.example.dto.EstudianteDTO(" +
                    "e.dni,e.nombre,e.apellido,e.edad,e.genero," +
                    "e.ciudadOrigen,e.numeroLibreta) " +
                    "FROM Estudiante e JOIN e.listCarreras m " +
                    "JOIN m.carrera c " +
                    "WHERE LOWER(c.nombre) = LOWER(:carrera) " +
                    "AND LOWER(e.ciudadOrigen) = LOWER(:ciudadOrigen)";
            List<EstudianteDTO> estudiantes = em.createQuery(jpql, EstudianteDTO.class)
                    .setParameter("carrera", carrera)
                    .setParameter("ciudadOrigen", ciudadOrigen)
                    .getResultList();
            em.close();
            return estudiantes;
        } catch (Exception e) {
            throw new RuntimeException("*** Error al obtener los estudiantes de " +
                    carrera + " de la ciudad de " + ciudadOrigen + " *** ", e);
        }
    }

    @Override
    public Estudiante findById(long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Estudiante estudiante = em.find(Estudiante.class, id);
        em.close();
        return estudiante;
    }
}
