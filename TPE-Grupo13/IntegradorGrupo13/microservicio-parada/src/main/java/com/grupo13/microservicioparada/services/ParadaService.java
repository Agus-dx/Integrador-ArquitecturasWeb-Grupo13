/**
 * 🛠️ Capa de Servicio (Service Layer) para la gestión de Paradas.
 *
 * Contiene la lógica de negocio y las operaciones de acceso a datos para la entidad Parada.
 * Es responsable de:
 * 1. Implementar operaciones CRUD y de actualización parcial (patch).
 * 2. **Geolocalización:** Implementa la búsqueda de paradas cercanas utilizando una
 * consulta de repositorio (`findParadasCercanas`) con un radio máximo definido.
 * 3. **Integración con Monopatines:** Utiliza el Feign Client (`MonopatinFeignClient`)
 * para orquestar la consulta y obtener el inventario de monopatines (todos o solo los
 * libres) asociados a una parada específica, lo que es esencial para la funcionalidad
 * de inicio de viaje.
 */
package com.grupo13.microservicioparada.services;

import com.grupo13.microservicioparada.dtos.ParadaDTO;
import com.grupo13.microservicioparada.dtos.ParadaPatchDTO;
import com.grupo13.microservicioparada.feignClients.MonopatinFeignClient;
import com.grupo13.microservicioparada.feignModel.Monopatin;
import com.grupo13.microservicioparada.model.Parada;
import com.grupo13.microservicioparada.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParadaService {
    private static final Double RADIO_MAX_BUSQUEDA = 1000.0;

    @Autowired
    private ParadaRepository repository;

    @Autowired
    private MonopatinFeignClient monopatinFeignClient;

    /**
     * Obtiene todas las paradas.
     */
    public List<ParadaDTO> findAll(){
        return repository.findAll().stream().map(ParadaDTO::new).toList();
    }

    /**
     * Busca una parada por ID. Lanza RuntimeException si no existe (NotFound).
     */
    public ParadaDTO findById(Long id){
        return repository.findById(id).map(ParadaDTO::new)
                .orElseThrow( () -> new RuntimeException("Parada no encontrada con ID: " + id));
    }

    /**
     * Busca paradas cercanas al usuario dentro de un radio.
     */
    public List<ParadaDTO> findParadasCercanas(Double latitud, Double longitud){
        return repository.findParadasCercanas(latitud, longitud, RADIO_MAX_BUSQUEDA).stream()
                .map(ParadaDTO::new).toList();
    }

    /**
     * Obtiene una parada y la lista de todos los monopatines asociados.
     */
    public Map<String, Object> findParadaConMonopatines(Long id){
        Parada parada = repository.findById(id)
                .orElseThrow( () -> new RuntimeException("Parada no encontrada con ID: " + id));
        Map<String,Object> retorno = new HashMap<>();
        retorno.put("parada", parada);
        List<Monopatin> monopatines = monopatinFeignClient.findMonopatinesByIdParada(id);
        if(monopatines.isEmpty()){
            retorno.put("monopatines", "No hay monopatines en esta parada");
        }
        else{
            retorno.put("monopatines", monopatines);
        }

        return retorno;
    }

    /**
     * Obtiene una parada y la lista de los monopatines en estado 'Libre'.
     */
    public Map<String, Object> findParadaConMonopatinesLibres(Long id) {
        Parada parada = repository.findById(id)
                .orElseThrow( () -> new RuntimeException("Parada no encontrada con ID: " + id));
        Map<String,Object> retorno = new HashMap<>();
        retorno.put("parada", parada);
        List<Monopatin> monopatinesLibres = monopatinFeignClient.findMonopatinesLibresByIdParada(id);
        if(monopatinesLibres.isEmpty()){
            retorno.put("monopatinesLibres", "No hay monopatines libres en esta parada");
        }
        else{
            retorno.put("monopatinesLibres", monopatinesLibres);
        }
        return retorno;
    }

    /**
     * Guarda una nueva parada.
     */
    public ParadaDTO save(Parada parada){
        return new ParadaDTO(repository.save(parada));
    }

    /**
     * Elimina una parada por ID. Lanza RuntimeException si no existe.
     */
    public void delete(Long id){
        Parada parada = repository.findById(id)
                .orElseThrow( () -> new RuntimeException("Parada no encontrada con ID: " + id));

        repository.delete(parada);
    }

    /**
     * Realiza una actualización parcial (patch) de una parada.
     */
    public ParadaDTO patch(Long id, ParadaPatchDTO paradaEditada){
        Parada parada = repository.findById(id)
                .orElseThrow( () -> new RuntimeException("Parada no encontrada con ID: " + id));
        if(paradaEditada.nombre()!=null)
            parada.setNombre(paradaEditada.nombre());
        if(paradaEditada.ciudad()!=null)
            parada.setCiudad(paradaEditada.ciudad());
        if(paradaEditada.direccion()!=null)
            parada.setDireccion(paradaEditada.direccion());
        if(paradaEditada.latitud()!=null)
            parada.setLatitud(paradaEditada.latitud());
        if(paradaEditada.longitud()!=null)
            parada.setLongitud(paradaEditada.longitud());

        return new ParadaDTO(repository.save(parada));
    }

    public List<Map<String, Object>> buscarMonopatinesLibresEnParadasCercanas(List<ParadaDTO> paradasCercanas) {
        List<Map<String, Object>> resultados = new ArrayList<>();

        for (ParadaDTO parada : paradasCercanas) {
            // Reutilizar la lógica que está en findParadaConMonopatinesLibres
            // Se asume que este método llama al Microservicio de Monopatines (FeignClient)
            Map<String, Object> resultadoParada = findParadaConMonopatinesLibres(parada.getId());

            // El resultado debe ser agregado solo si tiene monopatines
            if (!resultadoParada.get("monopatinesLibres").getClass().equals(String.class)) {
                resultados.add(resultadoParada);
            }
        }
        return resultados;
    }
}
