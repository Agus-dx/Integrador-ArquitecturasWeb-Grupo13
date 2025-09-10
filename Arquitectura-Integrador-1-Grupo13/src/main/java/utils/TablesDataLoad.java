package main.java.utils;

import main.java.persistence.daos.*;
import main.java.persistence.entities.*;
import main.java.persistence.factories.DAOFactory;
import main.java.persistence.factories.DBType;

import java.util.List;

public class TablesDataLoad {
	// Definición de los DAOs (Data Access Objects) que se usarán para la eliminación.
	private final ClienteDAO clienteDAO;
	private final ProductoDAO productoDAO;
	private final FacturaDAO facturaDAO;
	private final FacturaProductoDAO facturaProductoDAO;
	private CSVReader csvReader;
	private final DBType dbType = DBType.MYSQL;

	public TablesDataLoad() {
		// Obtiene la única instancia de la fábrica de DAOs (Singleton Pattern).
		// Esto asegura que la aplicación usa la misma fábrica en todo momento.
		DAOFactory f = DAOFactory.getInstance(dbType);
		this.clienteDAO = f.createClienteDAO();
		this.productoDAO = f.createProductoDAO();
		this.facturaDAO = f.createFacturaDAO();
		this.facturaProductoDAO = f.createFacturaProductoDAO();
		this.csvReader = new CSVReader();
	}

	/**
	 * Método principal que orquesta la carga de datos en todas las tablas.
	 * Llama a los métodos privados de carga para cada tipo de dato.
	 */
	public void loadData() {
		System.out.println("- Cargando datos en las tablas -");
		loadClientes("src/main/resources/csv/clientes.csv");
		loadProductos("src/main/resources/csv/productos.csv");
		loadFacturas("src/main/resources/csv/facturas.csv");
		loadFacturasProductos("src/main/resources/csv/facturas-productos.csv");
		System.out.println("- Datos cargados -");
	}

	/**
	 * Lee el archivo de clientes y los inserta en la base de datos.
	 */
	private void loadClientes(String filePath) {
		// Lee todos los clientes del archivo CSV y los guarda en una lista.
		List<Cliente> clientes = csvReader.readFileCliente(filePath);

		// Itera sobre la lista e inserta cada cliente en la base de datos.
		for (Cliente c : clientes) {
			clienteDAO.insert(c);
		}
		System.out.println("Clientes cargados: " + clientes.size());
	}

	/**
	 * Lee el archivo de productos y los inserta en la base de datos.
	 */
	private void loadProductos(String filePath) {
		List<Producto> productos = csvReader.readFileProducto(filePath);
		for (Producto p : productos) {
			productoDAO.insert(p);
		}
		System.out.println("Productos cargados: " + productos.size());
	}

	/**
	 * Lee el archivo de facturas y las inserta en la base de datos.
	 */
	private void loadFacturas(String filePath) {
		List<Factura> facturas = csvReader.readFileFactura(filePath);
		for (Factura f : facturas) {
			facturaDAO.insert(f);
		}
		System.out.println("Facturas cargadas: " + facturas.size());
	}

	/**
	 * Lee el archivo de facturas-productos y los inserta en la base de datos.
	 */
	private void loadFacturasProductos(String filePath) {
		List<FacturaProducto> facturasProductos = csvReader.readFileFacturaProducto(filePath);
		for (FacturaProducto fp : facturasProductos) {
			facturaProductoDAO.insert(fp);
		}
		System.out.println("Facturas-Productos cargados: " + facturasProductos.size());
	}
}