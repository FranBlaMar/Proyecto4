package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;

public class Usuario {
	
	@NotEmpty
	private String user;
	@NotEmpty
	private String contrasena;
	private String email;
	private String nombre;
	private String telefono;
	private String direccion;
	private ArrayList <Pedido> pedidosUsuario;
	
	public Usuario() {}
	
	public Usuario( String user,String contrasena, String email, String nombre, String telefono, String direccion) {
		this.user = user;
		this.contrasena = contrasena;
		this.email = email;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		this.pedidosUsuario = new ArrayList<>();
	}
	
	public Usuario(String user, String contrasena) {
		this.user=user;
		this.contrasena = contrasena;
	}


	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	
	public String getContrasena() {
		return contrasena;
	}
	
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public List<Pedido> getPedidosUsuario() {
		return pedidosUsuario;
	}
	
	public void anadirPedido(Pedido p) {
		this.pedidosUsuario.add(p);
	}

	@Override
	public int hashCode() {
		return Objects.hash(contrasena, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(contrasena, other.contrasena) && Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "Nombre de usuario: " + user + ", email: " + email + ", nombre: " + nombre + ", telefono: "
				+ telefono + ", direccion postal: " + direccion;
	} 

}
