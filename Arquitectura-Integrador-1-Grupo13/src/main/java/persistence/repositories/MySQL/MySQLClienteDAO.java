package main.java.persistence.repositories.MySQL;

import main.java.persistence.daos.ClienteDAO;
import main.java.persistence.entities.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLClienteDAO implements ClienteDAO {

	private final Connection con;

	public MySQLClienteDAO(Connection con) {
		this.con = con;
		createTableIfNotExists();
	}

	private void createTableIfNotExists() {
		String sql = "CREATE TABLE IF NOT EXISTS Cliente ("
				+ "idCliente INT AUTO_INCREMENT PRIMARY KEY, "
				+ "nombre VARCHAR(500) NOT NULL, "
				+ "email VARCHAR(150) UNIQUE NOT NULL"
				+ ");";
		try (Statement stmt = con.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println("*** Error al verificar las tablas ***");
			e.printStackTrace();
		}
	}

	@Override
	public Cliente findById(int id) {
		final String sql = "SELECT * FROM Cliente WHERE idCliente = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Cliente cliente = new Cliente(id, rs.getString("nombre"), rs.getString("email"));
					return cliente;
				} else {
					System.out.println("Cliente no encontrado con ID: " + id);
					return null;
				}
			}
		} catch (SQLException e) {
			System.out.println("*** Error al buscar el cliente por ID ***");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void insert(Cliente cliente) {
		final String sql = "INSERT INTO Cliente(idCliente, nombre, email) VALUES(?, ?, ?)";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, cliente.getIdCliente());
			ps.setString(2, cliente.getNombre());
			ps.setString(3, cliente.getEmail());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("*** Error al insertar el cliente ***");
			e.printStackTrace();
		}
	}

	@Override
	public void update(Cliente cliente) {
		final String sql = "UPDATE Cliente SET nombre = ?, email = ? WHERE idCliente = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, cliente.getNombre());
			ps.setString(2, cliente.getEmail());
			ps.setInt(3, cliente.getIdCliente());
			ps.executeUpdate();
			System.out.println("Cliente actualizado correctamente");
		} catch (SQLException e) {
			System.out.println("*** Error al actualizar el cliente ***");
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) {
		final String sql = "DELETE FROM Cliente WHERE idCliente = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.executeUpdate();
			System.out.println("Cliente eliminado correctamente");
		} catch (SQLException e) {
			System.out.println("*** Error al eliminar el cliente ***");
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAll() {
		final String sql = "DELETE FROM Cliente";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.executeUpdate();
			System.out.println("Todos los clientes eliminados correctamente");
		} catch (SQLException e) {
			System.out.println("*** Error al eliminar todos los clientes ***");
			e.printStackTrace();
		}
	}

	@Override
	public List<Cliente> findClientesOrdenadosPorFacturacion() {
		// - SELECT: elegimos las columnas del cliente (id, nombre, email)
		//           y calculamos la facturación total con SUM(cantidad * valor del producto)
		// - COALESCE(..., 0): si el cliente no tiene ventas, devuelve 0 en lugar de NULL
		// - FROM Cliente c: tomamos los datos de la tabla Cliente (alias c)
		// - LEFT JOIN Factura f: unimos con la tabla Factura para incluir todos los clientes, aunque no tengan facturas
		// - LEFT JOIN Factura_Producto fp: unimos con los productos de cada factura
		// - LEFT JOIN Producto p: obtenemos el valor de cada producto vendido
		// - GROUP BY: agrupamos por cliente para que SUM() calcule el total por cada uno
		// - ORDER BY total_facturado DESC: ordenamos de mayor a menor facturación
		final String sql = "SELECT c.idCliente, c.nombre, c.email, " +
				"COALESCE(SUM(fp.cantidad * p.valor), 0) as total_facturado " +
				"FROM Cliente c " +
				"LEFT JOIN Factura f ON c.idCliente = f.idCliente " +
				"LEFT JOIN Factura_Producto fp ON f.idFactura = fp.idFactura " +
				"LEFT JOIN Producto p ON fp.idProducto = p.idProducto " +
				"GROUP BY c.idCliente, c.nombre, c.email " +
				"ORDER BY total_facturado DESC";
		List<Cliente> clientes = new ArrayList<>();
		try (PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				int idCliente = rs.getInt("idCliente");
				String nombre = rs.getString("nombre");
				String email = rs.getString("email");
				float totalFacturado = rs.getFloat("total_facturado");
				Cliente cliente = new Cliente(idCliente, nombre, email, totalFacturado);
				clientes.add(cliente);
			}
		} catch (SQLException e) {
			System.out.println("*** Error al buscar clientes ordenados por facturación ***");
			e.printStackTrace();
		}
		return clientes;
	}

}
