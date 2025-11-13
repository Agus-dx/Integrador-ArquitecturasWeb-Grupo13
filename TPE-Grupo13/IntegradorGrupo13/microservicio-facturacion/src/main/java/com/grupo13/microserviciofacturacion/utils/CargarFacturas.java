package com.grupo13.microserviciofacturacion.utils;

import com.grupo13.microserviciofacturacion.entity.Factura;
import com.grupo13.microserviciofacturacion.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CargarFacturas implements CommandLineRunner{

    @Autowired
    FacturaRepository facturaRepository;

    public void CargarDatosIniciales(FacturaRepository facturaRepository) {
        // Lógica para cargar facturas iniciales en la base de datos
        Factura factura1 = new Factura();
        factura1.setNumeroFactura("FACT-2025-001");
        factura1.setImporte(150.0);
        factura1.setFechaEmision(new java.util.Date());
        factura1.setUsuarioId(1L);
        factura1.setTarifaId(1L);
        facturaRepository.save(factura1);
        
        Factura factura2 = new Factura();
        factura2.setNumeroFactura("FACT-2025-002");
        factura2.setImporte(200.0);
        factura2.setFechaEmision(new java.util.Date());
        factura2.setUsuarioId(2L);
        factura2.setTarifaId(1L);
        facturaRepository.save(factura2);
        
        Factura factura3 = new Factura();
        factura3.setNumeroFactura("FACT-2025-003");
        factura3.setImporte(250.0);
        factura3.setFechaEmision(new java.util.Date());
        factura3.setUsuarioId(3L);
        factura3.setTarifaId(2L);
        facturaRepository.save(factura3);
        
        Factura factura4 = new Factura();
        factura4.setNumeroFactura("FACT-2025-004");
        factura4.setImporte(300.0);
        factura4.setFechaEmision(new java.util.Date());
        factura4.setUsuarioId(4L);
        factura4.setTarifaId(2L);
        facturaRepository.save(factura4);
        
        Factura factura5 = new Factura();
        factura5.setNumeroFactura("FACT-2025-005");
        factura5.setImporte(350.0);
        factura5.setFechaEmision(new java.util.Date());
        factura5.setUsuarioId(5L);
        factura5.setTarifaId(3L);
        facturaRepository.save(factura5);
    }

    @Override
    public void run(String... args) throws Exception {
        CargarDatosIniciales(facturaRepository);
    }
}
