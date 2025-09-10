package main.java.persistence.factories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class ConnectionManager {

	private Connection connection;
	// Instancia única de ConnectionManager (patrón Singleton),
	// estática para que sea compartida por toda la aplicación,
	// y 'volatile' para garantizar visibilidad y seguridad en entornos multihilo.
	private static volatile ConnectionManager instance;

	private static final String DB_NAME = "integrador1";
	private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
	private static final String URL_WITHOUT_DB = "jdbc:mysql://localhost:3306/";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	private ConnectionManager() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Crear la base de datos si no existe
			createDatabaseIfNotExists();

			// Ahora conectarse a la base de datos integrador1
			this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("- Conexión exitosa a la base de datos " + DB_NAME + " -");
		} catch (SQLException e) {
			System.err.println("*** Error: MySQL ***");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("*** Error al conectar con la base de datos ***");
			e.printStackTrace();
		}
	}

	private void createDatabaseIfNotExists() {
		try (Connection con = DriverManager.getConnection(URL_WITHOUT_DB, USER, PASSWORD);
			 Statement stmt = con.createStatement()) {

			String sql = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
			stmt.executeUpdate(sql);
			System.out.println("Base de datos '" + DB_NAME + "' verificada/creada correctamente.");

		} catch (SQLException e) {
			System.err.println("*** Error al crear la base de datos ***");
			e.printStackTrace();
		}
	}

	// Metodo para obtener la instancia única de ConnectionManager
	public static ConnectionManager getInstance() {
		if (instance == null) {
			synchronized (ConnectionManager.class) {
				if (instance == null) {
					instance = new ConnectionManager();
				}
			}
		}
		return instance;
	}

	// Metodo para obtener la conexión a la base de datos
	public Connection getConnection() {
		return connection;
	}
}
