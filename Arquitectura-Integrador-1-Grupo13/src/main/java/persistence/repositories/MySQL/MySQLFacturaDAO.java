package main.java.persistence.repositories.MySQL;

import main.java.persistence.daos.FacturaDAO;
import main.java.persistence.entities.Factura;

import java.sql.*;

public class MySQLFacturaDAO implements FacturaDAO {
	
	private final Connection con;
	
	public MySQLFacturaDAO(Connection con) {
		this.con = con;
		createTableIfNotExists();
	}
	
	private void createTableIfNotExists() {
		String sql = "CREATE TABLE IF NOT EXISTS Factura ("
				+ "idFactura INT AUTO_INCREMENT PRIMARY KEY, "
				+ "idCliente INT, "
				+ "FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente) ON DELETE CASCADE"
				+ ");";
		try (Statement stmt = con.createStatement()) {
			stmt.execute(sql);
		} catch (Exception e) {
			System.out.println("*** Error al verificar las tablas ***");
			e.printStackTrace();
		}
	}

	@Override
	public Factura findById(int id) {
		final String sql = "SELECT * FROM Factura WHERE idFactura = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Factura factura = new Factura(id, rs.getInt("idCliente"));
					return factura;
				} else {
					System.out.println("Factura no encontrado con ID: " + id);
					return null;
				}
			}
		} catch (SQLException e) {
			System.out.println("*** Error al buscar la Factura por ID ***");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void insert(Factura factura) {
		final String sql = "INSERT INTO Factura (idFactura, idCliente) VALUES (?, ?)";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, factura.getIdFactura());
			ps.setInt(2, factura.getIdCliente());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("*** Error al insertar la factura ***");
			e.printStackTrace();
		} 
	}

	@Override
	public void update(Factura factura) {
		final String sql = "UPDATE Factura SET idCliente = ? WHERE idFactura = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, factura.getIdCliente());
			ps.setInt(2, factura.getIdFactura());
			ps.executeUpdate();
			System.out.println("Factura actualizada correctamente");
		} catch (SQLException e) {
			System.out.println("*** Error al actualizar la factura ***");
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(int id) {
		final String sql = "DELETE FROM Factura WHERE idFactura = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.executeUpdate();
			System.out.println("Factura eliminada correctamente");
		} catch (SQLException e) {
			System.out.println("*** Error al eliminar la factura ***");
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAll() {
		final String sql = "DELETE FROM Factura";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.executeUpdate();
			System.out.println("Todas las facturas eliminadas correctamente");
		} catch (SQLException e) {
			System.out.println("*** Error al eliminar todas las facturas ***");
			e.printStackTrace();
		}
		
	}

}
