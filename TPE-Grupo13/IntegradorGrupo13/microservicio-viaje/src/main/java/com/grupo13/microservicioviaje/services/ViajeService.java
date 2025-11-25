/**
 * 💡 Servicio de Negocio (Service Layer) para la entidad Viaje.
 *
 * Contiene la lógica transaccional para la gestión de viajes (iniciar, finalizar, CRUD).
 * Es responsable de coordinar las interacciones con otros microservicios
 * (Monopatín, Cuenta, Usuario, Tarifa, Parada y Facturación) a través de Feign Clients
 * para garantizar la integridad y el flujo de la operación de alquiler.
 */
package com.grupo13.microservicioviaje.services;

import com.grupo13.microservicioviaje.dtos.ReporteViajePeriodoDTO;
import com.grupo13.microservicioviaje.dtos.ReporteViajeUsuariosDTO;
import com.grupo13.microservicioviaje.dtos.ViajePatch;
import com.grupo13.microservicioviaje.dtos.ViajeDTO;
import com.grupo13.microservicioviaje.feignClients.*;
import com.grupo13.microservicioviaje.feignModels.*;
import com.grupo13.microservicioviaje.model.Viaje;
import com.grupo13.microservicioviaje.repository.ViajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors; // Importar Collectors

@Service
@RequiredArgsConstructor
public class ViajeService {

    private final ViajeRepository repository;
    private final MonopatinFeignClient monopatinFeignClient;
    private final CuentaFeignClient cuentaFeignClient;
    private final UsuarioFeignClient usuarioFeignClient;
    private final TarifaFeignClient tarifaFeignClient;
    private final ParadaFeignClient paradaFeignClient;
    private final FacturacionFeignClient facturacionFeignClient; // Asumo que FacturaEmitida y Factura están en el classpath

    /**
     * Inicia un nuevo viaje de alquiler.
     */
    @Transactional
    public ViajeDTO save(Viaje viaje) {
        // 1. Busco el monopatin y verifico existencia
        Monopatin monopatin = monopatinFeignClient.findById(viaje.getIdMonopatin());
        if(monopatin == null)
            throw new RuntimeException("Monopatin no encontrado con ID: " + viaje.getIdMonopatin());
        if(!monopatin.getEstado().equalsIgnoreCase("LIBRE"))
            // MonopatinNoDisponibleException -> IllegalArgumentException
            throw new IllegalArgumentException("Monopatin " + viaje.getIdMonopatin() + " no disponible. Estado actual: " + monopatin.getEstado());


        // 2. Busco la cuenta y verifico saldo
        Cuenta cuenta = cuentaFeignClient.findById(viaje.getIdCuenta());
        if(cuenta == null)
            throw new RuntimeException("Cuenta no encontrada con ID: " + viaje.getIdCuenta());
        if(cuenta.getSaldo().compareTo(BigDecimal.ZERO) == 0)
            // SaldoCuentaInsuficienteException -> IllegalArgumentException
            throw new IllegalArgumentException("Saldo insuficiente en la cuenta ID: " + cuenta.getId());

        // 3. Busco el usuario y verifico asociación
        Usuario usuario = usuarioFeignClient.findById(viaje.getIdUsuario());
        if(usuario == null)
            throw new RuntimeException("Usuario no encontrado con ID: " + viaje.getIdUsuario());
        if(!usuarioFeignClient.cuentaAsociada(usuario.getId(),cuenta.getId()))
            // CuentaNoAsociadaException -> IllegalArgumentException
            throw new IllegalArgumentException("La cuenta ID: " + cuenta.getId() + " no está asociada al usuario ID: " + usuario.getId());


        // 4. Busco paradas (asumo que se espera que ambas existan)
        Parada paradaInicio = paradaFeignClient.findById(viaje.getParadaOrigen());
        Parada paradaFin = paradaFeignClient.findById(viaje.getParadaDestino());
        if(paradaInicio == null)
            throw new RuntimeException("Parada de Origen no encontrada con ID: " + viaje.getParadaOrigen());
        if(paradaFin == null)
            throw new RuntimeException("Parada de Destino no encontrada con ID: " + viaje.getParadaDestino());

        // 5. Obtengo la tarifa
        Tarifa tarifa = tarifaFeignClient.findTarifaById(viaje.getIdTarifa());
        if(tarifa == null)
            throw new RuntimeException("Tarifa no encontrada con ID: " + viaje.getIdTarifa());

        // Seteo el estado del monopatin a activo
        monopatin = monopatinFeignClient.updateEstado(monopatin.getId(),"activo");

        return new ViajeDTO(repository.save(viaje));
    }

    /**
     * Finaliza un viaje activo, calcula el costo, resta el saldo y genera la factura.
     */
    @Transactional
    public Map<String,Object> finalizarViaje(Long id) {
        Viaje viaje = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado con ID: " + id)); // Excepción simplificada

        if(!viaje.isActivo())
            // ViajeFinalizadoException -> IllegalArgumentException
            throw new IllegalArgumentException("El viaje ID: " + id + " ya se encuentra finalizado.");

        Parada paradaDestino = paradaFeignClient.findById(viaje.getParadaDestino());
        if(paradaDestino == null)
            throw new RuntimeException("Parada de Destino no encontrada con ID: " + viaje.getParadaDestino()); // Excepción simplificada

        Monopatin monopatin = monopatinFeignClient.findById(viaje.getIdMonopatin());
        if(monopatin == null)
            throw new RuntimeException("Monopatin no encontrado con ID: " + viaje.getIdMonopatin()); // Excepción simplificada

        // Verifico que la parada destino sea valida y el monopatin este en ese lugar
        if(monopatin.getLatitud() != paradaDestino.getLatitud() &&
                monopatin.getLongitud() != paradaDestino.getLongitud()) {
            // DestinoNoValidoException -> IllegalArgumentException
            throw new IllegalArgumentException("El monopatin no se encuentra en la parada de destino ID: " + viaje.getParadaDestino());
        }

        // Precalculo el monto a restar y veo si la cuenta los tiene
        Cuenta cuenta = cuentaFeignClient.findById(viaje.getIdCuenta());
        Tarifa tarifa = tarifaFeignClient.findTarifaById(viaje.getIdTarifa());
        BigDecimal montoArestar = precalcularCostoViaje(tarifa, viaje);
        if(montoArestar.compareTo(cuenta.getSaldo()) > 0) {
            // SaldoCuentaInsuficienteException -> IllegalArgumentException
            throw new IllegalArgumentException("Saldo insuficiente en la cuenta ID: " + cuenta.getId());
        }
        Object facturaEcha = facturacionFeignClient.generarFactura(new Factura(viaje));
        if(facturaEcha != null) {
            // Resto el saldo de la cuenta
            cuenta = cuentaFeignClient.restarSaldo(cuenta.getId(),montoArestar);
        }

        // Seteo el estado del viaje a finalizado
        viaje.setFechaFin(LocalDateTime.now());
        viaje.setActivo(false);
        repository.save(viaje);

        // Seteo el estado del monopatin a libre
        monopatin = monopatinFeignClient.updateEstado(monopatin.getId(),"libre");

        // Genero un map con la factura y el viaje
        Map<String,Object> retorno = new HashMap<>();
        retorno.put("factura", facturaEcha);
        retorno.put("viaje", new ViajeDTO(viaje));
        retorno.put("cuenta",cuenta);

        return retorno;
    }

    private BigDecimal precalcularCostoViaje(Tarifa tarifa, Viaje viaje) {
        Double montoBase = tarifa.getMonto();
        Double montoExtra = tarifa.getMontoExtra();
        Double precioBase = montoBase+montoExtra;

        if(tarifa.getTiempoMaximoPausaMinutos() != 0 && viaje.getTiempoTotalMinutos() > tarifa.getTiempoMaximoPausaMinutos()) {
            Double recargo = precioBase * tarifa.getPorcentajeRecargoPausa();
            return BigDecimal.valueOf(precioBase + recargo);
        } else {
            return BigDecimal.valueOf(precioBase);
        }
    }

    /**
     * Busca un viaje por ID. Lanza RuntimeException si no lo encuentra.
     */
    @Transactional(readOnly = true)
    public ViajeDTO findById(Long id) {
        return new ViajeDTO(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado con ID: " + id)));
    }

    @Transactional(readOnly = true)
    public List<ViajeDTO> findAll() {
        return repository.findAll().stream().map(ViajeDTO::new).toList();
    }

    /**
     * Elimina un viaje por ID. Lanza RuntimeException si no lo encuentra.
     */
    @Transactional
    public void deleteById(Long id) {
        // Usa findById para asegurar que lanza la RuntimeException si no existe
        repository.findById(id).orElseThrow(() -> new RuntimeException("Viaje no encontrado con ID: " + id));
        repository.deleteById(id);
    }

    /**
     * Actualización parcial de un viaje (PATCH). Lanza RuntimeException si no lo encuentra.
     */
    @Transactional
    public ViajeDTO patch(Long id, ViajePatch viajePatch) {
        Viaje viaje = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado con ID: " + id));

        if(viajePatch.paradaOrigen() != null)
            viaje.setParadaOrigen(viajePatch.paradaOrigen());
        if(viajePatch.paradaDestino() != null)
            viaje.setParadaDestino(viajePatch.paradaDestino());
        if(viajePatch.idTarifa() != null)
            viaje.setIdTarifa(viajePatch.idTarifa());
        if(viajePatch.idMonopatin() != null)
            viaje.setIdMonopatin(viajePatch.idMonopatin());
        if(viajePatch.idUsuario() != null)
            viaje.setIdUsuario(viajePatch.idUsuario());
        if(viajePatch.idCuenta() != null)
            viaje.setIdCuenta(viajePatch.idCuenta());
        if(viajePatch.fechaInicio() != null)
            viaje.setFechaInicio(viajePatch.fechaInicio());
        if(viajePatch.fechaFin() != null)
            viaje.setFechaFin(viajePatch.fechaFin());
        if(viajePatch.tiempoTotalMinutos() != null)
            viaje.setTiempoTotalMinutos(viajePatch.tiempoTotalMinutos());
        if(viajePatch.kilometrosRecorridos() != null)
            viaje.setKilometrosRecorridos(viajePatch.kilometrosRecorridos());
        if(viajePatch.activo() != null)
            viaje.setActivo(viajePatch.activo());

        return new ViajeDTO(repository.save(viaje));
    }

    // c
    @Transactional(readOnly = true)
    public List<ReporteViajePeriodoDTO> getReporteViajeAnio(Integer anio, Long xViajes) {
        return repository.getReporteViajeAnio(anio, xViajes);
    }

    // e
    @Transactional(readOnly = true)
    public List<ReporteViajeUsuariosDTO> getReporteViajesPorUsuariosPeriodo(Integer anioDesde, Integer anioHasta, String rol) {
        if (rol != null) {
            Set<Long> usuarios = usuarioFeignClient.getUsuarioByRol(rol);
            // Si no tengo nada quiere decir que el rol no existe
            if(usuarios.isEmpty())
                throw new IllegalArgumentException("Tipo de rol no existe");

            return repository.getReportesViajesPorUsuariosPeriodoPorTipoUsuario(anioDesde, anioHasta, usuarios);
        }
        // Si no me pasan un rol traigo todos los reportes de todos los usuarios
        return repository.getReportesViajesPorUsuariosPeriodo(anioDesde, anioHasta);
    }

    public Map<String, Object> getReportesUsuarioYasociadosPerido( // Renombrado para mayor claridad
                                              Long idUsuario,
                                              Integer anioDesde,
                                              Integer anioHasta,
                                              Boolean incluirAsociados
    ) {
        // 1. **Paso Inicial: Validación del Usuario Principal**
        Usuario usuario = usuarioFeignClient.findById(idUsuario);
        if (usuario == null)
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);

        // 2. **Determinar la lista de IDs a buscar**
        Set<Long> usuarioIdsAConsultar = new HashSet<>();
        usuarioIdsAConsultar.add(idUsuario); // Siempre se incluye el usuario principal

        if (incluirAsociados) {
            // Traer todas las cuentas asociadas al usuario principal
            Set<Cuenta> cuentasUsuario = usuarioFeignClient.getCuentasByUsuario(idUsuario);

            // --- INICIO DE LA CORRECCIÓN CRÍTICA ---
            for (Cuenta cuenta : cuentasUsuario) {

                // LLAMADA FEIGN ANIDADA (Asumiendo que has añadido getUsuariosByCuenta al Feign Client)
                Set<Usuario> usuariosEnCuenta = usuarioFeignClient.getUsuariosByCuenta(cuenta.getId());

                // Extraer los IDs y añadirlos a la lista de consulta
                usuariosEnCuenta.stream()
                        .map(Usuario::getId)
                        .forEach(usuarioIdsAConsultar::add);
            }
            // --- FIN DE LA CORRECCIÓN CRÍTICA (Se eliminó la llamada a getUsuariosIdsByCuentas) ---
        }

        // 3. **Consulta de Uso Total (Duración)**
        //    Esto asume que el método del Repository y la @Query están correctos.
        Long duracionTotal = repository.getDuracionTotalUsoByUsuarios(
                usuarioIdsAConsultar,
                anioDesde,
                anioHasta
        );

        // 4. **Retorno Final**
        Map<String, Object> retorno = new HashMap<>();
        retorno.put("idUsuarioPrincipal", idUsuario);
        retorno.put("periodoAnios", anioDesde + " - " + anioHasta);
        retorno.put("incluyeAsociados", incluirAsociados);
        retorno.put("tiempoTotalUsoMinutos", duracionTotal != null ? duracionTotal : 0L);

        return retorno;
    }
}