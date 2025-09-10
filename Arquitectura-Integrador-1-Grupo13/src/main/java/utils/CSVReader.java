package main.java.utils;

import main.java.persistence.entities.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
	
	public List<Cliente> readFileCliente(String filePath) {
		List<Cliente> clientes = new ArrayList<>();
		try (CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(filePath))) {
			for (CSVRecord record : parser) {
				int id = Integer.parseInt(record.get("idCliente"));
				String nombre = record.get("nombre");
				String email = record.get("email");
				Cliente cliente = new Cliente(id, nombre, email);
				clientes.add(cliente);
			}
			System.out.println("Archivo clientes.csv leído correctamente");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return clientes;
	}
	
	public List<Producto> readFileProducto(String filePath) {
		List<Producto> productos = new ArrayList<>();
		
		try (CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(filePath))) {
			for (CSVRecord record : parser) {
				int id = Integer.parseInt(record.get("idProducto"));
				String nombre = record.get("nombre");
				Float valor = Float.parseFloat(record.get("valor"));
				Producto producto = new Producto(id, nombre, valor);
				productos.add(producto);
			}
			System.out.println("Archivo productos.csv leído correctamente");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return productos;
	}
	
	public List<Factura> readFileFactura(String filePath) {
		List<Factura> facturas = new ArrayList<>();
		
		try (CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(filePath))) {
			for (CSVRecord record : parser) {
				int idFactura = Integer.parseInt(record.get("idFactura"));
				int idCliente = Integer.parseInt(record.get("idCliente"));
				Factura factura = new Factura(idFactura, idCliente);
				facturas.add(factura);
			}
			System.out.println("Archivo facturas.csv leído correctamente");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return facturas;
	}
	
	public List<FacturaProducto> readFileFacturaProducto(String filePath) {
		List<FacturaProducto> facturaProductos = new ArrayList<>();
		
		try (CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(filePath))) {
			for (CSVRecord record : parser) {
				int idFactura = Integer.parseInt(record.get("idFactura"));
				int idProducto = Integer.parseInt(record.get("idProducto"));
				int cantidad = Integer.parseInt(record.get("cantidad"));
				FacturaProducto facturaProducto = new FacturaProducto(idFactura, idProducto, cantidad);
				facturaProductos.add(facturaProducto);
			}
			System.out.println("Archivo facturas-productos.csv leído correctamente");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return facturaProductos;
	}
	
}
