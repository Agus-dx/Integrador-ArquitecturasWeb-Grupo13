package main.java.persistence.daos;

import main.java.persistence.entities.Factura;

import java.util.List;

public interface FacturaDAO {
	
	Factura findById(int id);
	void insert(Factura factura);
	void update(Factura factura);
	void delete(int id);
	void deleteAll();

}
