package main.java.persistence.factories;

import main.java.persistence.daos.*;
import main.java.persistence.repositories.MySQL.*;

public class MySQLDAOFactory extends DAOFactory {
	
	@Override
	public ClienteDAO createClienteDAO() {
		return new MySQLClienteDAO(ConnectionManager.getInstance().getConnection());
	}

	@Override
	public ProductoDAO createProductoDAO() {
		return new MySQLProductoDAO(ConnectionManager.getInstance().getConnection());
	}

	@Override
	public FacturaDAO createFacturaDAO() {
		return new MySQLFacturaDAO(ConnectionManager.getInstance().getConnection());
	}

	@Override
	public FacturaProductoDAO createFacturaProductoDAO() {
		return new MySQLFacturaProductoDAO(ConnectionManager.getInstance().getConnection());
	}
}
