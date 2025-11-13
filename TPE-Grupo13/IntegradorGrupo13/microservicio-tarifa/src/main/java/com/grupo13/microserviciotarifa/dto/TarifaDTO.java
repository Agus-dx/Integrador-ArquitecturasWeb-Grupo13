package com.grupo13.microserviciotarifa.dto;

import com.grupo13.microserviciotarifa.entity.Tarifa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TarifaDTO {
    private Long id;
    private double monto;
    private double montoExtra;
    private Date fecha;
    private Integer tiempoMaximoPausaMinutos; // pausa sin recargo
    private Double porcentajeRecargoPausa; // porcentaje de recargo por exceso de pausa
    private Double cuotaMensualPremium;


    public TarifaDTO(double monto, double montoExtra, Date fecha) {
        this.monto = monto;
        this.montoExtra = montoExtra;
        this.fecha = fecha;
    }


    public TarifaDTO(Tarifa tarifa) {
        this.id = tarifa.getId();
        this.monto = tarifa.getMonto();
        this.montoExtra = tarifa.getMontoExtra();
        this.fecha = tarifa.getFecha();
        this.tiempoMaximoPausaMinutos = tarifa.getTiempoMaximoPausaMinutos();
        this.porcentajeRecargoPausa = tarifa.getPorcentajeRecargoPausa();
        this.cuotaMensualPremium = tarifa.getCuotaMensualPremium();
    }

    @Override
    public String toString() {
        return "TarifaDTO [monto=" + monto +
                ", montoExtra=" + montoExtra +
                ", tiempoMaximoPausaMinutos=" + tiempoMaximoPausaMinutos +
                ", porcentajeRecargoPausa=" + porcentajeRecargoPausa +
                ", cuotaMensualPremium=" + cuotaMensualPremium +
                ", fecha=" + fecha + "]";
    }
}
