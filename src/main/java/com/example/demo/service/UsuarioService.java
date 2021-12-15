package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.example.demo.model.Pedido;
import com.example.demo.model.Usuario;
@Service
public class UsuarioService {

	/**
	 * Array donde almacenar los usuarios existentes en la web de forma estática
	 */
	private List<Usuario> usersList = new ArrayList<>();
	
	/**
	 * 
	 * @return Lista de los usuarios existentes en la web
	 */
	public List<Usuario> findAll() {
		return usersList;
	}
	
	public void anadirPedidoAUsuario(Usuario us, Pedido p) {
		for(Usuario user: this.usersList) {
			if (user.equals(us)) {
				user.anadirPedido(p);
			}
		}
	}
	
	public void editarPedido(Pedido p, Usuario user) {
		for(Usuario us : this.usersList) {
			if(us.equals(user)) {
				us.getPedidosUsuario().remove(p);
				us.anadirPedido(p);
			}
		}
	}
	
	public void borrarPedido(Pedido p, Usuario us) {
		//Terminar
	}
	
	public List<Pedido> obtenerPedidosDeUsuario(Usuario us){
		int usIndex = this.usersList.indexOf(us);
		Usuario user = this.usersList.get(usIndex);
		return user.getPedidosUsuario();
	}
	
	public Usuario comprobarUser(Usuario us) {
		Usuario resultado;
		if (usersList.contains(us)) {
			int busquedaUsuario = usersList.indexOf(us);
			resultado = usersList.get(busquedaUsuario);
		}
		else {
			resultado = null;
		}
		return resultado;
	}
	
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
	 * Añado de forma estatica una lista de usuarios a la web
	 */
	@PostConstruct
	public void init() {
		usersList.addAll( Arrays.asList(new Usuario("J123", "123", "jorge@dominio.com","Jorgue", "911111111", "C/Rosales del campo Nº3"), new Usuario("FRAN", "BLANCO", "fran@dominio.com","Francisco", "922222222", "C/Puente romero Nº23"),new Usuario("F123", "111", "ff123@dominio.com","Maria", "933333333", "C/Perez de la luna Nº41")));
	}
	
}
