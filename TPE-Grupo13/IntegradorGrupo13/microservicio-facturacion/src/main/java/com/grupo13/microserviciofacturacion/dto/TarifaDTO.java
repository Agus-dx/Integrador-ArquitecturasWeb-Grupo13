package com.grupo13.microserviciofacturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TarifaDTO {
    private Long id;
    private double monto;
    private double montoExtra;
    private Date date;
    private Integer tiempoMaximoPausaMinutos;
    private Double porcentajeRecargoPausa;
    private Double cuotaMensualPremium;

    @Override
    public String toString() {
        return "TarifaDTO [monto=" + monto +
                ", montoExtra=" + montoExtra +
                ", tiempoMaximoPausaMinutos=" + tiempoMaximoPausaMinutos +
                ", porcentajeRecargoPausa=" + porcentajeRecargoPausa +
                ", cuotaMensualPremium=" + cuotaMensualPremium +
                ", date=" + date + "]";
    }
}
