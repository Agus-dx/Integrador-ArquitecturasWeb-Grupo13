/**
 * 🛠️ Capa de Servicio (Service Layer) para la gestión de Monopatines.
 *
 * Contiene la lógica de negocio principal para la gestión de activos móviles.
 * Es responsable de:
 * 1. Persistencia: Interactúa con el MonopatinRepository (MongoDB) para
 * operaciones CRUD, búsqueda por ID de Parada, y filtrado por Estado.
 * 2. Integración: Utiliza el **ParadaFeignClient** para verificar la existencia
 * de la Parada al crear/actualizar un Monopatín y para enriquecer la respuesta
 * con datos de la Parada (`getMonopatinConParada`).
 * 3. Validación: Valida el **Estado** del Monopatín usando un Enum antes de la
 * persistencia.
 * 4. Reportes: Implementa la lógica para generar reportes de Mantenimiento
 * basándose en los kilómetros recorridos.
 */
package com.grupo13.microserviciomonopatin.services;

import com.grupo13.microserviciomonopatin.dtos.MonopatinDTO;
import com.grupo13.microserviciomonopatin.dtos.MonopatinPatchDTO;
import com.grupo13.microserviciomonopatin.dtos.ReporteMantenimientoDTO;
import com.grupo13.microserviciomonopatin.feignClients.ParadaFeignClient;
import com.grupo13.microserviciomonopatin.feignModel.Parada;
import com.grupo13.microserviciomonopatin.model.Estado;
import com.grupo13.microserviciomonopatin.model.Monopatin;
import com.grupo13.microserviciomonopatin.repository.MonopatinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MonopatinService {
    private final ParadaFeignClient paradaFeignClient;

    private final MonopatinRepository repository;

    /**
     * Obtiene todos los monopatines y los convierte a DTOs.
     */
    @Transactional(readOnly = true)
    public List<MonopatinDTO> findAll() {
        List<Monopatin> monopatines = repository.findAll();
        return monopatines.stream().map(MonopatinDTO::new).toList();
    }

    /**
     * Busca un monopatin por ID. Lanza RuntimeException si no existe (NotFound).
     */
    @Transactional(readOnly = true)
    public MonopatinDTO findById(String id){
        Monopatin monopatin = repository.findById(id)
                .orElseThrow( () -> new RuntimeException("Monopatin no encontrado con ID: " + id)); // Simplificado: RuntimeException
        return new MonopatinDTO(monopatin);
    }

    /**
     * Obtiene un monopatin y su información de parada (usando Feign Client).
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getMonopatinConParada(String id) {
        // Lanza RuntimeException si no encuentra el monopatin.
        Monopatin monopatin = repository.findById(id)
                .orElseThrow( () -> new RuntimeException("Monopatin no encontrado con ID: " + id) );

        Map<String, Object> map = new HashMap<>();
        map.put("monopatin", new MonopatinDTO(monopatin));

        if(monopatin.getIdParada()==null)
            map.put("parada", "Este monopatin no tiene parada asignada");
        else {
            // Llama al microservicio de Parada
            map.put("parada", paradaFeignClient.findById(monopatin.getIdParada()));
        }
        return map;
    }

    /**
     * Guarda un nuevo monopatin. Valida el estado y la existencia de la parada.
     */
    @Transactional
    public MonopatinDTO save (Monopatin monopatin) {
        // 1. Verifico el estado
        String estadoPasado = monopatin.getEstado().toString().toUpperCase();
        if(Estado.perteneceAlEnum(estadoPasado)==null){
            // Lanza IllegalArgumentException si el estado es inválido (400)
            throw new IllegalArgumentException("Estado inválido: " + estadoPasado);
        }

        // 2. Verifico que la parada exista
        Parada parada = paradaFeignClient.findById(monopatin.getIdParada());
        if (parada == null) {
            // Lanza RuntimeException si la parada no existe (NotFound 404)
            throw new RuntimeException("Parada no encontrada con ID: " + monopatin.getIdParada().toString());
        }

        Monopatin monopatinNuevo = repository.save(monopatin);
        return new MonopatinDTO(monopatinNuevo);
    }

    /**
     * Elimina un monopatin por ID. Lanza RuntimeException si no lo encuentra.
     */
    @Transactional
    public void delete(String id){
        Monopatin monopatin = repository.findById(id)
                .orElseThrow( () -> new RuntimeException("Monopatin no encontrado con ID: " + id));

        repository.delete(monopatin);
    }

    /**
     * Realiza una actualización parcial (patch) de un monopatin.
     */
    @Transactional
    public MonopatinDTO patch(String id, MonopatinPatchDTO monopatinPatchDTO) {
        Monopatin monopatin = repository.findById(id)
                .orElseThrow( () -> new RuntimeException("Monopatin no encontrado con ID: " + id));

        // Aplica cambios si los campos no son nulos
        if(monopatinPatchDTO.estado()!=null)
            monopatin.setEstado(monopatinPatchDTO.estado());
        if(monopatinPatchDTO.latitud()!=null)
            monopatin.setLatitud(monopatinPatchDTO.latitud());
        if (monopatinPatchDTO.longitud()!=null)
            monopatin.setLongitud(monopatinPatchDTO.longitud());
        if (monopatinPatchDTO.kmRecorridos()!=null)
            monopatin.setKmRecorridos(monopatinPatchDTO.kmRecorridos());
        if (monopatinPatchDTO.tiempoUsado()!=null)
            monopatin.setTiempoUsado(monopatinPatchDTO.tiempoUsado());

        // Valida la nueva parada si se provee
        if(monopatinPatchDTO.idParada()!=null) {
            Parada paradaNueva = paradaFeignClient.findById(monopatinPatchDTO.idParada());
            if(paradaNueva==null)
                throw new RuntimeException("Parada no encontrada con ID: " + monopatinPatchDTO.idParada().toString());

            monopatin.setIdParada(monopatinPatchDTO.idParada());
        }

        return new MonopatinDTO(repository.save(monopatin));
    }

    /**
     * Establece el estado de un monopatin. Lanza excepción si el ID o el estado son inválidos.
     */
    @Transactional
    public MonopatinDTO setEstadoMonopatin(String id, String estado) {
        Monopatin monopatin = this.repository.findById(id)
                .orElseThrow( () -> new RuntimeException("Monopatin no encontrado con ID: " + id));

        // Valida el estado
        if(Estado.perteneceAlEnum(estado)==null){
            throw new IllegalArgumentException("Estado inválido: " + estado);
        }
        else{
            monopatin.setEstado(Estado.perteneceAlEnum(estado));
            return new MonopatinDTO(repository.save(monopatin));
        }
    }

    /**
     * Busca monopatines por ID de Parada.
     */
    @Transactional(readOnly = true)
    public List<MonopatinDTO> findMonopatinesByIdParada(Long idParada){
        return repository.findByIdParada(idParada).stream().map(MonopatinDTO::new).toList();
    }

    /**
     * Busca monopatines por estado y por ID de Parada.
     */
    @Transactional(readOnly=true)
    public List<MonopatinDTO> findMonopatinesPorEstadoByIdParada(Long idParada,String estado) {
        estado = estado.toUpperCase();

        // Valida el estado
        if(Estado.perteneceAlEnum(estado)==null){
            throw new IllegalArgumentException("Estado inválido: " + estado);
        }

        return repository.findByEstadoStringAndIdParada(estado,idParada)
                .stream().map(MonopatinDTO::new)
                .toList();
    }

    /**
     * Obtiene reportes de mantenimiento para monopatines que superan un kilometraje.
     */
    @Transactional(readOnly=true)
    public List<ReporteMantenimientoDTO> getReportesMantenimiento(Integer kmMantenimiento) {
        return repository.getReportesMantenimiento(kmMantenimiento)
                .stream().map(ReporteMantenimientoDTO::new).toList();
    }
}