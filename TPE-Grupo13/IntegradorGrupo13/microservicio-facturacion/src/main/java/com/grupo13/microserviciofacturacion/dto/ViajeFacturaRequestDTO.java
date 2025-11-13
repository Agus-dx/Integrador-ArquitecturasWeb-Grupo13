package com.grupo13.microserviciofacturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViajeFacturaRequestDTO {
    private Long viajeId;
    private Long usuarioId;
    private Double distanciaKm;
    private Long tarifaId;
    private Integer tiempoPausaMins;
}
