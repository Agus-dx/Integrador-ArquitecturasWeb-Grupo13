package main.java.persistence.entities;

public class Cliente {
	private int idCliente;
	private String nombre;
	private String email;
	private Float totalFacturado;
	
	public Cliente(int idCliente, String nombre, String email) {
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.email = email;
		this.totalFacturado = null;
	}

	public Cliente(int idCliente, String nombre, String email, float totalFacturado) {
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.email = email;
		this.totalFacturado = totalFacturado;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Float getTotalFacturado() {
		return totalFacturado;
	}
	
}
