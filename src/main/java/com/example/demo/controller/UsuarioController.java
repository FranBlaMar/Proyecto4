package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.PedidoService;
import com.example.demo.service.ProductoService;
import com.example.demo.service.UsuarioService;
import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;
import com.example.demo.utiles.Messages;

@Controller
public class UsuarioController {
	
	@Autowired
	private HttpSession sesion;
	
	@Autowired 
	private UsuarioService servicioUsuario;
	@Autowired
	private ProductoService servicioProducto;
	@Autowired
	private PedidoService servicioPedido;
	
	@GetMapping({"/", "/loginUsr"})
	public String loginUsuario (Model model) {
		model.addAttribute("usuario", new Usuario());
		return "login";
	}
	
	@PostMapping("/loginUsr/submit")
	public String comprobarLogin(@Valid @ModelAttribute("usuario") Usuario newUser,
			BindingResult errores, Model model) {
		
		String resultado;
		if (errores.hasErrors() || servicioUsuario.comprobarUser(newUser) == null) {
			resultado = "login";
			this.sesion.setAttribute("errorLogin", true);
			model.addAttribute("errorLogin", Messages.getErrorLogin());
		}
		else {
			resultado = "menu";
			model.addAttribute("nombre", servicioUsuario.comprobarUser(newUser).getNombre());
			this.sesion.setAttribute("usuario", newUser.getUser());
		}
		return resultado;
	}
	
	@GetMapping("/listaPedidos")
	public String listarPedidos(Model model) {
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			String usuario = this.sesion.getAttribute("usuario").toString();
			model.addAttribute("nombre", servicioUsuario.obtenerUsuario(usuario).getNombre());
			List<Pedido> listaPedidos = this.servicioPedido.obtenerPedidosDeUsuario(usuario);
			model.addAttribute("listaPedidos", listaPedidos);
			resultado = "lista";
		}
		return resultado;
	}
	
	@GetMapping("/realizarPedido")
	public String mostrarCatalogo(Model model) {
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			List<Producto> listaProductos = this.servicioProducto.findAll();
			model.addAttribute("listaProductos",listaProductos);
			resultado = "catalogo";
		}
		return resultado;
	}
	
	@PostMapping("/realizarPedido/añadirProductos")
	public String realizarPedido(@RequestParam("cantidad") int[] cantidades, Model model) {
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			Usuario us= this.servicioUsuario.obtenerUsuario(this.sesion.getAttribute("usuario").toString());
			Pedido p = new Pedido(us,us.getDireccion());
			this.servicioPedido.anadirProductosAPedido(this.servicioProducto.obtenerHashMap(cantidades), p);
			//Añadir precio total al pedido
			model.addAttribute("pedido",p);
			model.addAttribute("usuario",us);
			resultado = "resumen";
		}
		
		return resultado;
	}
	
	
}
