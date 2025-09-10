package main.java.persistence.daos;

import main.java.persistence.entities.Cliente;

import java.util.List;

public interface ClienteDAO {

	Cliente findById(int id);
	void insert(Cliente cliente);
	void update(Cliente cliente);
	void delete(int id);
	void deleteAll();
	List<Cliente> findClientesOrdenadosPorFacturacion();
	
}
