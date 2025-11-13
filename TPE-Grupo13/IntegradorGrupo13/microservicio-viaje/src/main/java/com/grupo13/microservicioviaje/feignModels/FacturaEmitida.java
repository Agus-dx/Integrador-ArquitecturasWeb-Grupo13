package com.grupo13.microservicioviaje.feignModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaEmitida {
    private Long id;
    private String numeroFactura;
    private Date fechaEmision;
    private double importe;
    private Long usuarioId;
    private Long tarifaId;
    private Long viajeId;
}
