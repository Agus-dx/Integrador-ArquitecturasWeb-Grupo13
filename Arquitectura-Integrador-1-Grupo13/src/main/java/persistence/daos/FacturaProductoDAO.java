package main.java.persistence.daos;

import main.java.persistence.entities.FacturaProducto;

import java.util.List;

public interface FacturaProductoDAO {

	FacturaProducto findById(int id, int id2);
	void insert(FacturaProducto facturaProducto);
	void update(FacturaProducto facturaProducto);
	void delete(int id, int id2);
	void deleteAll();
	
}
