/**
 * 📤 DTO Principal de Respuesta (Data Transfer Object) para la entidad Tarifa.
 *
 * Esta clase se utiliza para serializar y deserializar la información de las tarifas
 * que se comunica entre el Microservicio de Tarifas y los clientes externos (incluyendo
 * el Microservicio de Viajes). Su propósito es:
 * 1. Exponer de manera estructurada todos los parámetros de costo de la entidad Tarifa.
 * 2. Facilitar la conversión desde la entidad JPA (a través del constructor TarifaDTO(Tarifa)).
 * 3. Proporcionar constructores para la creación de nuevas tarifas y para la
 * representación completa de la información de precios.
 */
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
