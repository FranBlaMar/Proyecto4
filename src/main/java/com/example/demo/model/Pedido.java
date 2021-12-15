package com.example.demo.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Pedido {
	private static int creacionReferencia = 0000;
	private int referencia;
	private Usuario usuarioPedido;
	private HashMap <Producto,Integer> listaProductos;
	private LocalDate fechaPedido;
	private String direccion;
	private String telefono;
	private String email;
	private String tipoEnvio;
	private double precioTotal;
	
	/**
	 * Constructor de la clase Pedido
	 * @param Usuario del pedido
	 * @param Direccion del pedido
	 * @param Telefono del usuario del pedido
	 * @param Email del usuario del pedido
	 */
	public Pedido(Usuario usuarioPedido, String direccion, String telefono, String email) {
		super();
		this.referencia = creacionReferencia+1;
		creacionReferencia ++;
		this.usuarioPedido = usuarioPedido;
		this.fechaPedido = LocalDate.now();
		this.listaProductos = new HashMap<>();
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
	}

	public int getReferencia() {
		return referencia;
	}

	public Usuario getUsuarioPedido() {
		return usuarioPedido;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsuarioPedido(Usuario usuarioPedido) {
		this.usuarioPedido = usuarioPedido;
	}

	public Map<Producto,Integer> getListaProductos() {
		return listaProductos;
	}
	
	public void anadirProductos(HashMap<Producto,Integer> productos) {
		this.listaProductos = productos;
	}

	public LocalDate getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(LocalDate fechaPedido) {
		this.fechaPedido = fechaPedido;
	}
	
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTipoEnvio() {
		return tipoEnvio;
	}

	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(referencia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return referencia == other.referencia;
	}

	@Override
	public String toString() {
		return "Pedido con referencia " + referencia + ", usuarioPedido: " + usuarioPedido + ", listaProductos: "
				+ listaProductos + ", fechaPedido: " + fechaPedido + "\n";
	}
	
}
