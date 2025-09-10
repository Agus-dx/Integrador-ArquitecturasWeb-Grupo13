package main.java;

import main.java.persistence.daos.*;
import main.java.persistence.entities.*;
import main.java.persistence.factories.*;
import main.java.utils.*;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		new TablesDataDelete().deleteAllData();
		System.out.println("--------------------------------");

		new TablesDataLoad().loadData();
		System.out.println("--------------------------------");

		DAOFactory f = DAOFactory.getInstance(DBType.MYSQL);


		// 3- Obtener el producto mas recaudador
		ProductoDAO productoDAO = f.createProductoDAO();
		Producto productoMasRecaudador = productoDAO.findProductoMasRecaudador();
		if (productoMasRecaudador == null) {
			System.out.println("No se pudo determinar el producto más recaudador");
		}
		System.out.println("------------------------------------");


		// 4- Obtener clientes ordenados por facturación
		ClienteDAO clienteDAO = f.createClienteDAO();
		List<Cliente> clientesOrdenados = clienteDAO.findClientesOrdenadosPorFacturacion();
		System.out.println("Lista de clientes ordenados de manera descendiente por monto total facturado:");
		for (int i = 0; i < clientesOrdenados.size(); i++) {
			Cliente cliente = clientesOrdenados.get(i);
			String totalFacturado = "$" + cliente.getTotalFacturado();
			System.out.println((i + 1) + ". (" + cliente.getNombre() +
							 " - ID:" + cliente.getIdCliente() +
							 " - Email: " + cliente.getEmail() +
							 ") - Total facturado: " + totalFacturado);
		}
		
		System.out.println("- Fin del programa -");
	}

}
