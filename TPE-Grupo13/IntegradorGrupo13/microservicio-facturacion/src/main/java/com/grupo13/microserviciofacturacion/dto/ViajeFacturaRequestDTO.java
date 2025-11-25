/**
 * 📥 DTO de Entrada/Solicitud (Request DTO) para la Creación de Factura.
 *
 * Esta clase se utiliza para recibir los datos finales de un viaje (una vez
 * terminado y medido) que son esenciales para que el Microservicio de Facturación
 * pueda calcular el importe y registrar el cobro.
 * * Campos Clave:
 * 1. viajeId: Referencia al registro del viaje que se está facturando.
 * 2. usuarioId: Identificador de quién debe pagar (necesario para la lógica Premium).
 * 3. distanciaKm: Métrica variable para el cálculo del costo.
 * 4. tarifaId: Identificador de la regla de precio aplicada (necesario para
 * obtener el monto de Tarifa Service).
 * 5. tiempoPausaMins: Métrica necesaria para aplicar el posible recargo por pausa.
 */
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
