package com.example.demo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.service.PedidoService;
import com.example.demo.service.ProductoService;
import com.example.demo.service.UsuarioService;
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
			resultado = "redirect:/";
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
	
	@PostMapping("/listaPedidos")
	public String listarPedidos(Model model) {
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			resultado = "lista";
		}
		return resultado;
	}
	
	
}
