package main.java.persistence.daos;

import main.java.persistence.entities.Producto;

import java.util.List;

public interface ProductoDAO {

	Producto findById(int id);
	void insert(Producto producto);
	void update(Producto producto);
	void delete(int id);
	void deleteAll();
	Producto findProductoMasRecaudador();
}
