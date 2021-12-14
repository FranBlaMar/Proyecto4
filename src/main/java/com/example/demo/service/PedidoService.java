package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;

@Service
public class PedidoService {
	@Autowired 
	private UsuarioService servicioUsuario;
	@Autowired 
	private ProductoService servicioProducto;
	
	private List<Pedido> pedidosList = new ArrayList<>();

	public List<Pedido> findAll() {
		return pedidosList;
	}
	
	public void anadirPedidoRepositorio(Pedido p) {
		this.pedidosList.add(p);
	}
	
	
	public List<Pedido> obtenerPedidosDeUsuario(Usuario usuario){
		List <Pedido> resultado = new ArrayList<> ();
		for(Pedido pedido: this.pedidosList) {
			if(pedido.getUsuarioPedido().equals(usuario)) {
				resultado.add(pedido);
			}
		}
		return resultado;
	}
	
	public void anadirProductosAPedido(HashMap<Producto,Integer> productos, Pedido p) {
		p.anadirProductos(productos);
	}
	public void anadirTipoEnvio(String envio, Pedido p) {
		p.setTipoEnvio(envio);
	}
	
	public Pedido obtenerPedidoPorReferencia(int referencia){
		Pedido resultado = null;
		for(Pedido pedido: this.pedidosList) {
			if(pedido.getReferencia() == referencia) {
				resultado = pedido;
			}
		}
		return resultado;
	}
	
	

	
	@PostConstruct
	public void init() {
		Usuario usuario1 = servicioUsuario.obtenerUsuario("F123");
		Pedido pedido1 = new Pedido(usuario1,usuario1.getDireccion());
		pedido1.setTipoEnvio("ESTAMDAR");
		pedido1.setPrecioTotal(76.19);
		HashMap<Producto,Integer> productos1 = new HashMap<>();
		productos1.put(this.servicioProducto.obtenerProductoPorId("111A"), 2);
		productos1.put(this.servicioProducto.obtenerProductoPorId("444D"), 4);
		productos1.put(this.servicioProducto.obtenerProductoPorId("333C"), 1);
		pedido1.anadirProductos(productos1);
		this.servicioUsuario.anadirPedidoAUsuario(usuario1, pedido1);
		
		Pedido pedido2 = new Pedido(usuario1, usuario1.getDireccion());
		pedido2.setTipoEnvio("EXPRES");
		pedido2.setPrecioTotal(75.49);
		HashMap<Producto,Integer> productos2 = new HashMap<>();
		productos2.put(this.servicioProducto.obtenerProductoPorId("222B"), 2);
		productos2.put(this.servicioProducto.obtenerProductoPorId("555E"), 1);
		productos2.put(this.servicioProducto.obtenerProductoPorId("666F"), 1);
		pedido2.anadirProductos(productos2);
		this.servicioUsuario.anadirPedidoAUsuario(usuario1, pedido2);
		
		Usuario user2 = servicioUsuario.obtenerUsuario("J123");
		Pedido pedido3 = new Pedido(user2,user2.getDireccion());
		pedido3.setTipoEnvio("ESTANDAR");
		pedido3.setPrecioTotal(209.68);
		HashMap<Producto,Integer> productos3 = new HashMap<>();
		productos3.put(this.servicioProducto.obtenerProductoPorId("666F"), 4);
		productos3.put(this.servicioProducto.obtenerProductoPorId("111A"), 3);
		productos3.put(this.servicioProducto.obtenerProductoPorId("333C"), 2);
		pedido3.anadirProductos(productos3);
		this.servicioUsuario.anadirPedidoAUsuario(user2, pedido3);
		
		pedidosList.addAll(Arrays.asList(pedido1,pedido2,pedido3));
	}
}