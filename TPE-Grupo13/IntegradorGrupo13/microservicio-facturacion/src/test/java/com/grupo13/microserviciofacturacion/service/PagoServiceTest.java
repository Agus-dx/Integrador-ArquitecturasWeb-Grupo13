package com.grupo13.microserviciofacturacion.service;

import com.grupo13.microserviciofacturacion.client.MercadoPagoClient;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PagoServiceTest {

    @Test
    void procesarPago_conMockDeMercadoPago() {

        MercadoPagoClient mpMock = mock(MercadoPagoClient.class);
        when(mpMock.realizarPago(anyDouble()))
                .thenReturn("APROBADO");

        PagoService pagoService = new PagoService(mpMock);

        String resultado = pagoService.procesarPago(3000);

        assertEquals("APROBADO", resultado);
        verify(mpMock, times(1)).realizarPago(anyDouble());
        System.out.println("Resultado del pago = " + resultado);

    }
}
