package com.grupo13.microserviciofacturacion.client;

import org.springframework.stereotype.Component;

@Component
public class MercadoPagoClient {

    // Simulación de un servicio externo REAL
    public String realizarPago(double monto) {
        // En producción esto llamaría a MercadoPago
        // Aquí lo dejamos simple porque será mockeado en los tests
        return "REAL_MP_RESPONSE";
    }
}
