/**
 * 📥 DTO de Solicitud (Request DTO) para el Microservicio de Facturación.
 *
 * Esta clase actúa como un objeto de transporte de datos, tomando la información
 * relevante de un 'Viaje' (ID, usuario, distancia, pausa) y estructurándola
 * para ser enviada al Microservicio de Facturación. Su constructor facilita
 * la conversión de una entidad 'Viaje' a la estructura de datos que el
 * servicio de facturación espera para calcular y emitir la factura final.
 */
package com.grupo13.microservicioviaje.feignModels;

import com.grupo13.microservicioviaje.model.Viaje;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factura {
    private Long viajeId;
    private Long usuarioId;
    private Integer distanciaKm;
    private Long tarifaId;
    private Integer tiempoPausaMinutos;

    public Factura(Viaje viaje) {
        this.viajeId = viaje.getId();
        this.usuarioId = viaje.getIdUsuario();
        this.distanciaKm = viaje.getKilometrosRecorridos();
        this.tarifaId = viaje.getIdTarifa();
        this.tiempoPausaMinutos = viaje.getTiempoPausa();

    }
}
