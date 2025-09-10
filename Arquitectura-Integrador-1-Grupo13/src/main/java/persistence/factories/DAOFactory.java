package main.java.persistence.factories;

import main.java.persistence.daos.*;

public abstract class DAOFactory {

	private static volatile DAOFactory instance;

	public static DAOFactory getInstance(DBType dbType) {
		if (instance == null) {
			synchronized (DAOFactory.class) {
				if (instance == null) {
					switch (dbType) {
						case MYSQL:
							instance = new MySQLDAOFactory();
							break;
						// case POSTGRESQL:
						// 	instance = new PostgreSQLDAOFactory();
						// 	break;
						default:
							throw new IllegalArgumentException("*** Tipo de base de datos no soportado ***");
					}
				}
			}
		}
		return instance;
	}

	public abstract ClienteDAO createClienteDAO();
	public abstract ProductoDAO createProductoDAO();
	public abstract FacturaDAO createFacturaDAO();
	public abstract FacturaProductoDAO createFacturaProductoDAO();
}
