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
