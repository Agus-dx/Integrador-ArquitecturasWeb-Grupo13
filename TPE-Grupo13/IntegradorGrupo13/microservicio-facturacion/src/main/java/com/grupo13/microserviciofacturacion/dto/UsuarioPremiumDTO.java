package com.grupo13.microserviciofacturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPremiumDTO {
    private Long id;
    private String nombre;
    private boolean esPremium;
    private Double cupoMensualKm;
    private Double kmConsumidosMes;
    private Integer mesActual;
    private Integer anioActual;
}
