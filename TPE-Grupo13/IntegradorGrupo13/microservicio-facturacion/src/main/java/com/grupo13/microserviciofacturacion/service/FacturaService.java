package com.grupo13.microserviciofacturacion.service;

import com.grupo13.microserviciofacturacion.dto.TotalFacturadoDTO;
import com.grupo13.microserviciofacturacion.feignClient.TarifaFeignClient;
import com.grupo13.microserviciofacturacion.feignClient.UsuarioFeignClient;
import com.grupo13.microserviciofacturacion.dto.TarifaDTO;
import com.grupo13.microserviciofacturacion.dto.UsuarioPremiumDTO;
import com.grupo13.microserviciofacturacion.dto.ViajeFacturaRequestDTO;
import com.grupo13.microserviciofacturacion.entity.Factura;
import com.grupo13.microserviciofacturacion.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private TarifaFeignClient tarifaFeignClient;

    @Autowired
    private UsuarioFeignClient usuarioFeignClient;

    @Transactional(readOnly = true)
    public List<Factura> findAll(){
        return facturaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Factura findById(Long id){
        return facturaRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Factura> findByFechaBetween(Date fecha1, Date fecha2){
        return facturaRepository.findByFechaBetween(fecha1,fecha2);
    }

    @Transactional
    public Factura save(Factura factura){
        return facturaRepository.save(factura);
    }

    @Transactional
    public void delete(Long id){
        facturaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public TotalFacturadoDTO getTotalFacturadoPorRangoMeses(int anio, int mesDesde, int mesHasta) {
        TotalFacturadoDTO totalFacturado = new TotalFacturadoDTO();
        totalFacturado.setAnio(anio);
        totalFacturado.setMesDesde(mesDesde);
        totalFacturado.setMesHasta(mesHasta);
        totalFacturado.setTotalFacturado(facturaRepository.getTotalFacturadoPorRangoMeses(anio, mesDesde, mesHasta));
        return totalFacturado;
    }

    @Transactional
    public Factura crearFacturaDesdeViaje(ViajeFacturaRequestDTO request) {
        TarifaDTO tarifa = tarifaFeignClient.findTarifaById(request.getTarifaId()).getBody();
        if (tarifa == null) {
            throw new RuntimeException("Tarifa no encontrada con ID: " + request.getTarifaId());
        }
        UsuarioPremiumDTO usuario = null;
        try {
            usuario = usuarioFeignClient.getUsuarioPremium(request.getUsuarioId()).getBody();
        } catch (Exception e) {
            usuario = null;
        }
        Double importe = calcularImporteConPremium(
                request.getDistanciaKm(),
                request.getTiempoPausaMins(),
                tarifa,
                usuario
        );
        if (usuario != null && usuario.isEsPremium()) {
            try {
                usuarioFeignClient.actualizarKmConsumidos(
                        usuario.getId(),
                        request.getDistanciaKm()
                );
            } catch (Exception e) {
                System.err.println("Error actualizando km consumidos: " + e.getMessage());
            }
        }
        Factura factura = new Factura();
        factura.setNumeroFactura(generarNumeroFacturaUUID());
        factura.setFechaEmision(new Date());
        factura.setImporte(importe);
        factura.setUsuarioId(request.getUsuarioId());
        factura.setTarifaId(request.getTarifaId());
        factura.setViajeId(request.getViajeId());
        return facturaRepository.save(factura);
    }

    private String generarNumeroFacturaUUID() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    private Double calcularImporteConPremium(Double distanciaKm, Integer tiempoPausaMinutos,
                                             TarifaDTO tarifa, UsuarioPremiumDTO usuario) {
        if (usuario == null || !usuario.isEsPremium()) {
            return calcularImporteNormal(distanciaKm, tiempoPausaMinutos, tarifa);
        }
        Double kmRestantesCupo = usuario.getCupoMensualKm() - usuario.getKmConsumidosMes();
        if (kmRestantesCupo > 0) {

            // 2a
            if (distanciaKm <= kmRestantesCupo) {
                // Solo cobra tarifa fija + recargo por pausa si aplica
                return calcularSoloTarifaFija(tiempoPausaMinutos, tarifa);
            }

            // 2b
            Double kmExcedentes = distanciaKm - kmRestantesCupo;
            return calcularImporteParcialPremium(kmExcedentes, tiempoPausaMinutos, tarifa);
        }

        // 2c
        return calcularImportePremiumSinCupo(distanciaKm, tiempoPausaMinutos, tarifa);
    }

    private Double calcularImporteNormal(Double distanciaKm, Integer tiempoPausaMinutos, TarifaDTO tarifa) {
        Double importeVariable = distanciaKm * tarifa.getMonto();
        Double tarifaFija = tarifa.getMontoExtra();
        Double importeBase = importeVariable + tarifaFija;
        if (tiempoPausaMinutos != null &&
                tarifa.getTiempoMaximoPausaMinutos() != null &&
                tarifa.getPorcentajeRecargoPausa() != null &&
                tiempoPausaMinutos > tarifa.getTiempoMaximoPausaMinutos()) {

            Double recargo = importeBase * tarifa.getPorcentajeRecargoPausa();
            return importeBase + recargo;
        }
        return importeBase;
    }

    private Double calcularSoloTarifaFija(Integer tiempoPausaMinutos, TarifaDTO tarifa) {
        Double importeBase = tarifa.getMontoExtra();
        if (tiempoPausaMinutos != null &&
                tarifa.getTiempoMaximoPausaMinutos() != null &&
                tarifa.getPorcentajeRecargoPausa() != null &&
                tiempoPausaMinutos > tarifa.getTiempoMaximoPausaMinutos()) {

            Double recargo = importeBase * tarifa.getPorcentajeRecargoPausa();
            return importeBase + recargo;
        }

        return importeBase;
    }

    private Double calcularImporteParcialPremium(Double kmExcedentes, Integer tiempoPausaMinutos, TarifaDTO tarifa) {
        Double importeVariable = kmExcedentes * tarifa.getMonto() * 0.5;
        Double tarifaFija = tarifa.getMontoExtra();
        Double importeBase = importeVariable + tarifaFija;
        if (tiempoPausaMinutos != null &&
                tarifa.getTiempoMaximoPausaMinutos() != null &&
                tarifa.getPorcentajeRecargoPausa() != null &&
                tiempoPausaMinutos > tarifa.getTiempoMaximoPausaMinutos()) {
            Double recargo = importeBase * tarifa.getPorcentajeRecargoPausa();
            return importeBase + recargo;
        }
        return importeBase;
    }

    private Double calcularImportePremiumSinCupo(Double distanciaKm, Integer tiempoPausaMinutos, TarifaDTO tarifa) {
        Double importeVariable = distanciaKm * tarifa.getMonto() * 0.5;
        Double tarifaFija = tarifa.getMontoExtra();
        Double importeBase = importeVariable + tarifaFija;
        if (tiempoPausaMinutos != null &&
                tarifa.getTiempoMaximoPausaMinutos() != null &&
                tarifa.getPorcentajeRecargoPausa() != null &&
                tiempoPausaMinutos > tarifa.getTiempoMaximoPausaMinutos()) {

            Double recargo = importeBase * tarifa.getPorcentajeRecargoPausa();
            return importeBase + recargo;
        }
        return importeBase;
    }

}
