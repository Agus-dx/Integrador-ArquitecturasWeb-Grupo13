package com.grupo13.microserviciofacturacion.service;

import com.grupo13.microserviciofacturacion.client.MercadoPagoClient;
import org.springframework.stereotype.Service;

@Service
public class PagoService {

    private final MercadoPagoClient mercadoPagoClient;

    public PagoService(MercadoPagoClient mercadoPagoClient) {
        this.mercadoPagoClient = mercadoPagoClient;
    }

    public String procesarPago(double monto) {
        return mercadoPagoClient.realizarPago(monto);
    }
}
