/**
 * 💰 Modelo Feign (o DTO de Comunicación) para la entidad Tarifa.
 *
 * Esta clase representa la información de la tarifa obtenida desde el
 * Microservicio de Tarifas. Contiene los detalles necesarios (montos, recargos,
 * tiempos máximos de pausa) para que el Microservicio de Viajes pueda calcular
 * el costo final de un alquiler.
 * La anotación @JsonIgnoreProperties(ignoreUnknown = true) garantiza que la
 * deserialización no falle si el servicio de origen envía campos adicionales.
 */
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
