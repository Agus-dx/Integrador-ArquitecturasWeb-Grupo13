package com.grupo13.microserviciofacturacion.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class FacturaDTO {
    private String numeroFactura;
    private String fechaEmision;
    private double importe;

    @Override
    public String toString() {
        return "FacturaDTO{" +
                ", numeroFactura='" + numeroFactura + '\'' +
                ", fechaEmision='" + fechaEmision + '\'' +
                ", importe=" + importe +
                '}';
    }

}
