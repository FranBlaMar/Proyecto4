package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.example.demo.model.ComparatorFecha;
import com.example.demo.model.Pedido;
import com.example.demo.model.Usuario;
@Service
public class UsuarioService {


	private List<Usuario> usersList = new ArrayList<>();
	
	/**
	 * 
	 * @return Lista de los usuarios existentes en la web
	 */
	public List<Usuario> findAll() {
		return usersList;
	}
	
	/**
	 * Metodo para añadir pedidos a un usuario
	 * @param Usuario al que añadirle el pedido
	 * @param Pedido el cual añadimos a la lista de pedidos del usuario
	 */
	public void anadirPedidoAUsuario(Usuario us, Pedido p) {
		for(Usuario user: this.usersList) {
			if (user.equals(us)) {
				user.anadirPedido(p);
			}
		}
	}
	
	
	/**
	 * Método para cambiar un pedido de un usuario por el mismo pedido editado
	 * @param Pedido ya editado
	 * @param Usuario al que vamos a editarle un pedido
	 */
	public void editarPedido(Pedido p, Usuario user) {
		for(Usuario us : this.usersList) {
			if(us.equals(user)) {
				us.getPedidosUsuario().remove(p);
				us.anadirPedido(p);
			}
		}
	}
	
	/**
	 * Metodo para borrar un pedido de la lista de pedidos de un usuario
	 * @param Pedido que deseamos borrar
	 * @param Usuario al que pertenece el pedido
	 */
	public void borrarPedido(Pedido p, Usuario us) {
		this.usersList.get(this.usersList.indexOf(us)).getPedidosUsuario().remove(p);
	}
	
	/**
	 * Metodo para obtener los pedidos de un usuario, los cuales devuelve ordenados por fecha
	 * @param Usuario del que deseamos obtener los pedidos
	 * @return Devuelve la lista de pedidos del usuario
	 */
	public List<Pedido> obtenerPedidosDeUsuario(Usuario us){
		int usIndex = this.usersList.indexOf(us);
		Usuario user = this.usersList.get(usIndex);
		List<Pedido> resultado = user.getPedidosUsuario();
		Collections.sort(resultado,new ComparatorFecha());
		return resultado;
	}
	
	/**
	 * Metodo para comprobar si un usuario existe en nuestro servidor
	 * @param Usuario creado en el logni, que usaremos para comparar con ususarios existentes
	 * @return Devuelve el usuario que hayamos encontrado, en caso de no existir devuelve nulo
	 */
	public Usuario comprobarUser(Usuario us) {
		Usuario resultado;
		//si contiene al usuario lo devuelve
		if (usersList.contains(us)) {
			int busquedaUsuario = usersList.indexOf(us);
			resultado = usersList.get(busquedaUsuario);
		}
		else {
			//si no existe el usuario devuelve nulo
			resultado = null;
		}
		return resultado;
	}
	
	/**
	 * Metodo para obtener un usuario mediante su nombre de usuario
	 * @param nombre de usuario de un Usuario del servidor
	 * @return Devuelve el usuario que está almacenado en el servidor
	 */
	public Usuario obtenerUsuario(String user) {
		Usuario usuarioObtenido = null;
		for (Usuario us:usersList){
			if (us.getUser().equals(user)) {
				usuarioObtenido = us;
			}
		}
		return usuarioObtenido;
	}
	
	/**
	 * Metodo postConstruct para añadir usuarios de forma estatica para realizar comprobaciones durante el desarrollo del proyecto
	 */
	@PostConstruct
	public void init() {
		usersList.addAll( Arrays.asList(new Usuario("J123", "123", "jorge@dominio.com","Jorgue", "911111111", "C/Rosales del campo Nº3"), new Usuario("FRAN", "BLANCO", "fran@dominio.com","Francisco", "922222222", "C/Puente romero Nº23"),new Usuario("F123", "111", "ff123@dominio.com","Maria", "933333333", "C/Perez de la luna Nº41")));
	}
	
}
