package main.java.utils;

import main.java.persistence.daos.*;
import main.java.persistence.factories.DAOFactory;
import main.java.persistence.factories.DBType;

public class TablesDataDelete {

	// Definición de los DAOs (Data Access Objects) que se usarán para la eliminación.
	private final ClienteDAO clienteDAO;
	private final ProductoDAO productoDAO;
	private final FacturaDAO facturaDAO;
	private final FacturaProductoDAO facturaProductoDAO;
	private final DBType dbType = DBType.MYSQL;

	public TablesDataDelete() {
		// Obtiene la única instancia de la fábrica de DAOs (Singleton Pattern).
		DAOFactory f = DAOFactory.getInstance(dbType);
		// Asigna las implementaciones DAO específicas de MySQL obtenidas de la fábrica.
		this.clienteDAO = f.createClienteDAO();
		this.productoDAO = f.createProductoDAO();
		this.facturaDAO = f.createFacturaDAO();
		this.facturaProductoDAO = f.createFacturaProductoDAO();
	}

	/**
	 * Elimina todos los datos de las tablas de la base de datos en el orden correcto
	 */
	public void deleteAllData() {
		try {
			System.out.println("- Eliminando datos de las tablas -");
			// Primero se eliminan las tablas "hijas" (las que tienen claves foráneas),
			// y luego las tablas "padre".
			this.facturaProductoDAO.deleteAll(); // Depende de Factura y Producto
			this.facturaDAO.deleteAll(); // Depende de Cliente
			this.productoDAO.deleteAll(); // No depende de nadie
			this.clienteDAO.deleteAll(); // No depende de nadie

			System.out.println("- Todas las tablas han sido limpiadas -");
		} catch (Exception e) {
			System.out.println("*** Error al eliminar los datos de las tablas ***");
			e.printStackTrace();
		}
	}
}