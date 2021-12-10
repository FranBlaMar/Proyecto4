package com.example.demo.model;

public class Producto {
	private String id;
	private String nombre;
	private String imagen;
	private double precio;
	
	
	public Producto(String id, String nombre, String imagen, double precio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.imagen = imagen;
		this.precio = precio;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}

}
