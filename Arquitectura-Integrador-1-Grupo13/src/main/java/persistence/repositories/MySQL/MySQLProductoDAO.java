package main.java.persistence.repositories.MySQL;

import main.java.persistence.daos.ProductoDAO;
import main.java.persistence.entities.Producto;

import java.sql.*;

public class MySQLProductoDAO implements ProductoDAO {
	
	private final Connection con;

	public MySQLProductoDAO(Connection con) {
		this.con = con;
		createTableIfNotExists();
	}

	private void createTableIfNotExists() {
		String sql = "CREATE TABLE IF NOT EXISTS Producto ("
				+ "idProducto INT AUTO_INCREMENT PRIMARY KEY, "
				+ "nombre VARCHAR(45) NOT NULL, "
				+ "valor FLOAT NOT NULL"
				+ ");";
		try (Statement stmt = con.createStatement()) {
			stmt.execute(sql);
		} catch (Exception e) {
			System.out.println("*** Error al verificar las tablas ***");
			e.printStackTrace();
		}
	}

	@Override
	public Producto findById(int id) {
		final String sql = "SELECT * FROM Producto WHERE idProducto = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Producto producto = new Producto(id, rs.getString("nombre"), rs.getFloat("valor"));
					return producto;
				} else {
					System.out.println("Producto no encontrado con ID: " + id);
					return null;
				}
			}
		} catch (Exception e) {
			System.out.println("*** Error al buscar el Producto por ID ***");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void insert(Producto producto) {
		final String sql = "INSERT INTO Producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, producto.getIdProducto());
			ps.setString(2, producto.getNombre());
			ps.setFloat(3, producto.getValor());
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("*** Error al insertar el producto ***");
			e.printStackTrace();
		}
	}

	@Override
	public void update(Producto producto) {
		final String sql = "UPDATE Producto SET nombre = ?, valor = ? WHERE idProducto = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, producto.getNombre());
			ps.setFloat(2, producto.getValor());
			ps.setInt(3, producto.getIdProducto());
			ps.executeUpdate();
			System.out.println("Producto actualizado correctamente");
		} catch (Exception e) {
			System.out.println("*** Error al actualizar el producto ***");
			e.printStackTrace();
		}

	}

	@Override
	public void delete(int id) {
		final String sql = "DELETE FROM Producto WHERE idProducto = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.executeUpdate();
			System.out.println("Producto eliminado correctamente");
		} catch (Exception e) {
			System.out.println("*** Error al eliminar el producto ***");
			e.printStackTrace();
		}

	}

	@Override
	public void deleteAll() {
		final String sql = "DELETE FROM Producto";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.executeUpdate();
			System.out.println("Todos los productos eliminados correctamente");
		} catch (Exception e) {
			System.out.println("*** Error al eliminar todos los productos ***");
			e.printStackTrace();
		}
	}


	@Override
	public Producto findProductoMasRecaudador() {
		// Consulta SQL que obtiene el producto que más recaudó
		// - SELECT: elegimos las columnas que queremos traer (id, nombre, valor del producto)
		//           y además calculamos la recaudación total = SUM(cantidad vendida * valor del producto)
		// - FROM: tomamos los datos de la tabla Producto (alias p)
		// - INNER JOIN: unimos con la tabla Factura_Producto (alias fp) para saber cuántas veces
		//               se vendió cada producto y en qué cantidad
		// - GROUP BY: agrupamos por producto (id, nombre y valor) para que SUM() se calcule por cada producto
		// - ORDER BY: ordenamos los resultados de mayor a menor según la recaudación total
		// - LIMIT 1: nos quedamos solo con el producto con mayor recaudación
		final String sql = "SELECT p.idProducto, p.nombre, p.valor, " +
				"SUM(fp.cantidad * p.valor) as recaudacion_total " +
				"FROM Producto p " +
				"INNER JOIN Factura_Producto fp ON p.idProducto = fp.idProducto " +
				"GROUP BY p.idProducto, p.nombre, p.valor " +
				"ORDER BY recaudacion_total DESC " +
				"LIMIT 1";
		try (PreparedStatement ps = con.prepareStatement(sql);
			 // ResultSet: representa la "tabla de resultados" que devuelve la consulta SQL.
			 // Nos permite recorrer fila por fila y leer los valores de cada columna con métodos como getInt(), getString(), getFloat(), etc.
			 ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				int idProducto = rs.getInt("idProducto");
				String nombre = rs.getString("nombre");
				float valor = rs.getFloat("valor");
				float recaudacionTotal = rs.getFloat("recaudacion_total");
				System.out.println("El producto más recaudador fue: (" + nombre +
				" - ID: " + idProducto + ") - Recaudación total: $" + recaudacionTotal + " - Valor unitario: $" + valor);
				return new Producto(idProducto, nombre, valor);

			} else {
				System.out.println("No se encontraron productos con ventas");
				return null;
			}
		} catch (Exception e) {
			System.out.println("*** Error al buscar el producto más recaudador ***");
			e.printStackTrace();
			return null;
		}
	}

}
