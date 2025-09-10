package main.java.persistence.repositories.MySQL;

import main.java.persistence.daos.FacturaProductoDAO;
import main.java.persistence.entities.FacturaProducto;

import java.sql.*;

public class MySQLFacturaProductoDAO implements FacturaProductoDAO {
	
	private final Connection con;
	
	public MySQLFacturaProductoDAO(Connection con) {
		this.con = con;
		createTableIfNotExists();
	}
	
	private void createTableIfNotExists() {
		String sql = "CREATE TABLE IF NOT EXISTS Factura_Producto ("
				+ "idFactura INT, "
				+ "idProducto INT, "
				+ "cantidad INT NOT NULL, "
				+ "PRIMARY KEY (idFactura, idProducto), "
				+ "FOREIGN KEY (idFactura) REFERENCES Factura(idFactura) ON DELETE CASCADE, "
				+ "FOREIGN KEY (idProducto) REFERENCES Producto(idProducto) ON DELETE CASCADE"
				+ ");";
		try (Statement stmt = con.createStatement()) {
			stmt.execute(sql);
		} catch (Exception e) {
			System.out.println("*** Error al verificar las tablas ***");
			e.printStackTrace();
		}
	}

	@Override
	public FacturaProducto findById(int id, int id2) {
		final String sql = "SELECT * FROM Factura_Producto WHERE idFactura = ? AND idProducto = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.setInt(2, id2);
			try (ResultSet rs = ps.executeQuery()) {
				// Si se encuentra el registro, se crea y retorna el objeto FacturaProducto
				if (rs.next()) {
					FacturaProducto facturaProducto = new FacturaProducto(
						rs.getInt("idFactura"),
						rs.getInt("idProducto"),
						rs.getInt("cantidad")
					);
					return facturaProducto;
				} else {
					System.out.println("Registro no encontrado con ID: " + id);
					return null;
				}
			}
		} catch (Exception e) {
			System.out.println("*** Error al buscar el registro por ID ***");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void insert(FacturaProducto facturaProducto) {
		final String sql = "INSERT INTO Factura_Producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, facturaProducto.getIdFactura());
			ps.setInt(2, facturaProducto.getIdProducto());
			ps.setInt(3, facturaProducto.getCantidad());
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("*** Error al insertar el registro de FacturaProducto ***");
			e.printStackTrace();
		}
	}

	@Override
	public void update(FacturaProducto facturaProducto) {
		final String sql = "UPDATE Factura_Producto SET cantidad = ? WHERE idFactura = ? AND idProducto = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, facturaProducto.getCantidad());
			ps.setInt(2, facturaProducto.getIdFactura());
			ps.setInt(3, facturaProducto.getIdProducto());
			ps.executeUpdate();
			System.out.println("Registro de FacturaProducto actualizado correctamente");
		} catch (Exception e) {
			System.out.println("*** Error al actualizar el registro de FacturaProducto ***");
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id, int id2) {
		final String sql = "DELETE FROM Factura_Producto WHERE idFactura = ? AND idProducto = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.setInt(2, id2);
			ps.executeUpdate();
			System.out.println("Registro de FacturaProducto eliminado correctamente");
		} catch (Exception e) {
			System.out.println("*** Error al eliminar el registro de FacturaProducto ***");
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteAll() {
		final String sql = "DELETE FROM Factura_Producto";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.executeUpdate();
			System.out.println("Todos los registros de FacturaProducto eliminados correctamente");
		} catch (Exception e) {
			System.out.println("*** Error al eliminar todos los registros de FacturaProducto ***");
			e.printStackTrace();
		}
	}
}
