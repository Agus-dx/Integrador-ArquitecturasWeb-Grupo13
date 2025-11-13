package com.grupo13.microservicioviaje.feignModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tarifa {
    private Double monto;
    private Double montoExtra;
    private Date fecha;
    private Integer tiempoMaximoPausaMinutos;
    private Double porcentajeRecargoPausa;
    private Double cuotaMensualPremium;
}
