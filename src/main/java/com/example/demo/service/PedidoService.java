package com.example.demo.service;

import java.time.LocalDate;
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

	/**
	 * 
	 * @return La lista con todos los pedidods almacenados en el servidor
	 */
	public List<Pedido> findAll() {
		return pedidosList;
	}
	
	/**
	 * Metodo para crear y añadir un pedido a la lista de pedidos
	 * @param Usuario al que pertenece el pedido
	 * @param Double del precio total del pedido
	 * @param HashMap con los productos y las cantidades comprados
	 * @return El Pedido ya añadido 
	 */
	public Pedido crearYAnadirPedido(Usuario us,double precioTotal, HashMap<Producto,Integer> cantidadesProductos) {
		Pedido p = new Pedido(us,us.getDireccion(),us.getTelefono(), us.getEmail());
		p.setPrecioTotal(precioTotal);
		p.anadirProductos(cantidadesProductos);
		this.pedidosList.add(p);
		return p;
	}
	
	/**
	 * Metodo para añadir a un Pedido la lista de productos
	 * @param HashMap con los productos y las cantidades compradas
	 * @param Pedido al que añadirle la lista de productos
	 */
	public void anadirProductosAPedido(HashMap<Producto,Integer> productos, Pedido p) {
		p.anadirProductos(productos);
	}
	
	/**
	 * Metodo para añadirle a un pedido el tipo de envio y la direccion cambiada
	 * @param String con el tipo de envio
	 * @param String con la direccion nueva si la ha cambiado, o la alamacenada en el usuario si no la ha modificado
	 * @param Int con la referencia del pedido
	 */
	public void anadirTipoEnvioyDireccion(String envio, String direccion,int pedido) {
		obtenerPedidoPorReferencia(pedido).setTipoEnvio(envio);
		obtenerPedidoPorReferencia(pedido).setDireccion(direccion);
	}
	
	/**
	 * Metodo para modificar un pedido y modificarlo en la lista de pedidos
	 * @param Usuario al que pertenece el pedido editado
	 * @param Precio total editado o el antiguo
	 * @param HashMap de productos comprados editado o el antiguo
	 * @param String del tipo de envio editado o el antiguo
	 * @param int con el numero de referencia del pedido a editar
	 * @param String con la direccion editado o el antiguo
	 * @param String con el telefono editado o el antiguo
	 * @param String con el email editado o el antiguo
	 * @return Devuelve el Pedido editado
	 */
	public Pedido editarPedido(Usuario us,double precioTotal, HashMap<Producto,Integer> cantidadesProductos, String tipoEnvio, int refe, String direccion, String telefono, String email) {
		Pedido resultado = null;
		for(Pedido p: this.pedidosList) {
			//Obtengo el pedido con la referencia que le indico
			if(p.getReferencia() == refe) {
				//Le modifico todos los datos antiguos por los nuevos
				p.anadirProductos(cantidadesProductos);
				p.setUsuarioPedido(us);
				p.setDireccion(direccion);
				p.setTelefono(telefono);
				p.setEmail(email);
				p.setPrecioTotal(precioTotal);
				p.setTipoEnvio(tipoEnvio);
				resultado = p;
			}
		}
		return resultado;
	}
	
	/**
	 * Metodo para borrar un pedido de la lista de pedidos
	 * @param Pedido que deseamos borrar
	 */
	 
	public void borrarPedido(Pedido p) {
		this.pedidosList.remove(p);
	}
	
	/**
	 * Metodo para obtener un pedido de la lista de pedidos mediante un numero de referencia
	 * @param int con el numero de referencia del pedido que deseamos obtener
	 * @return Pedido que estamos buscando
	 */
	public Pedido obtenerPedidoPorReferencia(int referencia){
		Pedido resultado = null;
		for(Pedido pedido: this.pedidosList) {
			if(pedido.getReferencia() == referencia) {
				resultado = pedido;
			}
		}
		return resultado;
	}
	
	

	/**
	 * Creación manual de pedidos para comprobación durante desarrollo
	 */
	@PostConstruct
	public void init() {
		//Simplemente creo pedidos de forma estatica para poder hacer comprobaciones durante el desarrollo
		Usuario usuario1 = servicioUsuario.obtenerUsuario("F123");
		Pedido pedido1 = new Pedido(usuario1,usuario1.getDireccion(), usuario1.getTelefono(), usuario1.getEmail());
		pedido1.setTipoEnvio("ESTANDAR");
		pedido1.setPrecioTotal(76.19);
		HashMap<Producto,Integer> productos1 = new HashMap<>();
		productos1.put(this.servicioProducto.obtenerProductoPorId("111A"), 2);
		productos1.put(this.servicioProducto.obtenerProductoPorId("444D"), 4);
		productos1.put(this.servicioProducto.obtenerProductoPorId("333C"), 1);
		pedido1.anadirProductos(productos1);
		this.servicioUsuario.anadirPedidoAUsuario(usuario1, pedido1);
		
		Pedido pedido2 = new Pedido(usuario1, usuario1.getDireccion(), usuario1.getTelefono(), usuario1.getEmail());
		pedido2.setTipoEnvio("EXPRESS");
		LocalDate fecha = LocalDate.parse("2021-11-30");
		pedido2.setFechaPedido(fecha);
		pedido2.setPrecioTotal(75.49);
		HashMap<Producto,Integer> productos2 = new HashMap<>();
		productos2.put(this.servicioProducto.obtenerProductoPorId("222B"), 2);
		productos2.put(this.servicioProducto.obtenerProductoPorId("555E"), 1);
		productos2.put(this.servicioProducto.obtenerProductoPorId("666F"), 1);
		pedido2.anadirProductos(productos2);
		this.servicioUsuario.anadirPedidoAUsuario(usuario1, pedido2);
		
		Usuario user2 = servicioUsuario.obtenerUsuario("J123");
		Pedido pedido3 = new Pedido(user2,user2.getDireccion(), user2.getTelefono(), user2.getEmail());
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