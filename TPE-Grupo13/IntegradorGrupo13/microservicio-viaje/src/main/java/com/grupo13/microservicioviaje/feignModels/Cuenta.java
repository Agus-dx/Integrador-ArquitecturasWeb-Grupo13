package com.grupo13.microservicioviaje.feignModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {
    private Long id;
    private BigDecimal saldo;
    private String estado;
    private String tipoCuenta;
    private Double kmConsumidosMes;
    private LocalDate fechaRenovacionCupo;
}
