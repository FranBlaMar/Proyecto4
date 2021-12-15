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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	/**
	 * Pantalla inicial de logeo del usuario
	 * @param model para pasar al formulario un objeto usuario
	 * @return String direccion html
	 */
	@GetMapping({"/", "/loginUsr"})
	public String loginUsuario (Model model) {
		model.addAttribute("usuario", new Usuario());
		return "login";
	}
	
	/**
	 * Comprobacion de existencia de usuario y redireccion a menu de usuario
	 * @param newUser creado en el formulario
	 * @param errores producidos al crear el usuario
	 * @param model para añadir mensaje de error
	 * @param redirectAttribute
	 * @return String direccion html
	 */
	@PostMapping("/loginUsr/submit")
	public String comprobarLogin(@Valid @ModelAttribute("usuario") Usuario newUser,
			BindingResult errores, Model model, RedirectAttributes redirectAttribute) {
		
		String resultado;
		//Si hay errores en el formulario o la persona intenta acceder sin iniciar sesion, le reenvia al login
		if (errores.hasErrors() || servicioUsuario.comprobarUser(newUser) == null) {
			resultado = "redirect:/";
			this.sesion.setAttribute("errorLogin", true);
			redirectAttribute.addFlashAttribute("errorLogeo", Messages.getErrorLogin());
			model.addAttribute("errorLogin", Messages.getErrorLogin());
		}
		else {
			resultado = "menu";
			this.sesion.setAttribute("usuario", newUser.getUser());
		}
		return resultado;
	}
	
	/**
	 * Mostrar la lista de pedidos del usuario
	 * @param model para pasar al html el nombre del usuario y la lista de pedidos
	 * @return String direccion html
	 */
	@GetMapping("/listaPedidos")
	public String listarPedidos(Model model) {
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			//Obtengo el usuario
			Usuario usuario = this.servicioUsuario.obtenerUsuario(this.sesion.getAttribute("usuario").toString());
			model.addAttribute("nombre", usuario.getNombre());
			//Obtengo los pedidos del usuario anterior
			List<Pedido> listaPedidos = this.servicioUsuario.obtenerPedidosDeUsuario(usuario);
			model.addAttribute("listaPedidos", listaPedidos);
			resultado = "lista";
		}
		return resultado;
	}
	
	/**
	 * Mostrar el catalogo de productos para realizar el pedido
	 * @param model para pasar al html la lista de productos del servidor
	 * @return String direccion html
	 */
	@GetMapping("/realizarPedido")
	public String mostrarCatalogo(Model model) {
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			//Obtengo todos los productos del servidor para mostrarlos en el catalogo
			List<Producto> listaProductos = this.servicioProducto.findAll();
			model.addAttribute("listaProductos",listaProductos);
			resultado = "catalogo";
		}
		return resultado;
	}
	
	/**
	 * Creacion del pedido en caso de que se haya elegido minimo un producto
	 * @param Lista de int con las cantidades de productos comprados
	 * @param model para pasar el pedido y usuario al html
	 * @param redirectAttributes para pasar mensaje de error al html
	 * @return String direccion html
	 */
	@PostMapping("/realizarPedido/añadirProductos")
	public String realizarPedido(@RequestParam("cantidad") int[] cantidades, Model model, RedirectAttributes redirectAttributes) {
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			boolean comprobarCarrito = false;
			//recorro el array con las cantidades indicadas en el formulario, para ver que ha añadido minimo 1 producto
			for (int i = 0; i < cantidades.length && !comprobarCarrito; i++) {
				if (cantidades[i] > 0) {
					comprobarCarrito = true;
				}
			}
			//Si no ha añadido ningun producto, le redirecciona al catalogo y le muestra error
			if(!comprobarCarrito) {
				resultado ="redirect:/realizarPedido";
				redirectAttributes.addFlashAttribute("errorCatalogo", Messages.getErrorCatalogo());
			}
			else {
				//si ha añadido productos correctamente, obtengo el usuario de la sesion
				Usuario us= this.servicioUsuario.obtenerUsuario(this.sesion.getAttribute("usuario").toString());
				//Obtendo el hashmap con los productos y las cantidades
				HashMap<Producto,Integer> cantidadesProductos = (HashMap<Producto, Integer>) this.servicioProducto.obtenerHashMap(cantidades);
				//Obtengo el precio total del pedido
				double precioTotal = this.servicioProducto.obtenerPrecioTotal(cantidadesProductos);
				//Creo y añadio el pedido a la lista de pedidos
				Pedido p = this.servicioPedido.crearYAnadirPedido(us, precioTotal, cantidadesProductos);
				//Le añado el pedido al usuario
				this.servicioUsuario.anadirPedidoAUsuario(us, p);
				model.addAttribute("pedido",p);
				model.addAttribute("usuario",us);
				resultado = "resumen";
			}
		}
		
		return resultado;
	}
	
	/**
	 * Cerrar sesion del usuario
	 * @return String redireccion
	 */
	@GetMapping("/cerrarSesion")
	public String cerrarSesion(){
		this.sesion.invalidate();
		return "redirect:/";
	}

	/**
	 * Añadir al pedido el tipo de envio y la dirección
	 * @param String con el tipo de envio introducido en el formulario
	 * @param String con la direccion del pedido
	 * @param int con el numero de referencia del pedido realizado
	 * @return String direccion html
	 */
	@GetMapping("/realizarPedido/resumen/finish/{refe}")
	public String finalizarPedido(@RequestParam("envio") String envio, @RequestParam("direccion") String direccion,@PathVariable("refe") int refe) {
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			//añado al pedido el tipo de envio y la direccion
			this.servicioPedido.anadirTipoEnvioyDireccion(envio, direccion, refe);
			resultado="redirect:/listaPedidos";
		}
		return resultado;
	}
	
	/**
	 * Obtención del pedido que se desea editar y envio al formulario de edicion
	 * @param model para pasar al html el usuario y el pedido
	 * @param int con el numero de referencia del pedido a editar
	 * @return String direccion html
	 */
	@GetMapping("/editarPedido/{refe}")
	public String editarPedido(Model model, @PathVariable int refe) {
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			//Obtengo el usuario
			Usuario us= this.servicioUsuario.obtenerUsuario(this.sesion.getAttribute("usuario").toString());
			model.addAttribute("usuario",us);
			//Obtengo el pedido mediante la referencia, para mostrar los datos en el formulario de edicion
			model.addAttribute("pedido", this.servicioPedido.obtenerPedidoPorReferencia(refe));
			
			resultado="editar";
		}
		return resultado;
	}
	
	
	/**
	 * Obtencion de datos editados del pedido y modificacion de este en el servidor
	 * @param array con los productos del pedido
	 * @param array con las cantidades del pedido
	 * @param int con el numero de referencia del pedido a editar
	 * @param String con la direccion del pedido
	 * @param String con el telefono del pedido
	 * @param String con el email del pedido
	 * @param String con el tipo de pedido del pedido
	 * @param redirectAttribute para pasar al html un mensaje de error
	 * @return String redireccion html
	 */
	@PostMapping("/editarPedido/realizarCambios/{refe}")
	public String finalizarEdicionPedido(@RequestParam String[] productos, 
		@RequestParam int[] cantidades, @PathVariable int refe, 
		@RequestParam String direccion, @RequestParam String telefono, 
		@RequestParam String email, @RequestParam String envio, RedirectAttributes redirectAttribute) {
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			HashMap<Producto,Integer> listaDeProductos = new HashMap<>();
			//Recorro la lista de productos del pedido, para cambiarle las cantidades o eliminarlos en caso de que el usuario o haya indicado
			for(int i = 0; i < productos.length; i++) {
				int cantidad = cantidades[i];
				Producto producto = this.servicioProducto.obtenerProductoPorId(productos[i]);
				if(cantidad > 0) {
					listaDeProductos.put(producto,cantidad);
				}
			}
			//si el usuario ha eliminado todos los productos, le redirecciono a la edicion y le muestro error
			if (listaDeProductos.size() == 0) {
				redirectAttribute.addFlashAttribute("errorEditar", Messages.getErrorEditar());
				resultado = "redirect:/editarPedido/{refe}";
			}
			else {
				//Obtengo el precio total tras las modificaciones de los productos
				double precioTotal = this.servicioProducto.obtenerPrecioTotal(listaDeProductos);
				//Obtengo el usuario y edito el pedido en la lista de pedidos y en la lista de pedidos del usuario
				Usuario us= this.servicioUsuario.obtenerUsuario(this.sesion.getAttribute("usuario").toString());
				Pedido p = this.servicioPedido.editarPedido(us, precioTotal, listaDeProductos,envio, refe, direccion, telefono, email);
				this.servicioUsuario.editarPedido(p, us);
				resultado ="redirect:/listaPedidos";
			}
		}
		return resultado;
	}
	
	/**
	 * Borrado de pedido del servidor
	 * @param int con el numero de refencia del pedido a borrar
	 * @return String direccion html
	 */
	@GetMapping ("/borrarPedido/{refe}")
	public String borrarPedido(@PathVariable int refe) {
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			//Obtengo el usuario y el pedidod que se desea borrar
			Usuario us= this.servicioUsuario.obtenerUsuario(this.sesion.getAttribute("usuario").toString());
			Pedido p = this.servicioPedido.obtenerPedidoPorReferencia(refe);
			//Borro el pedido de ambas listas
			this.servicioUsuario.borrarPedido(p, us);
			this.servicioPedido.borrarPedido(p);
			resultado = "redirect:/listaPedidos";
		}
		return resultado;
	}
	
	/**
	 * Volver al menu de usuario
	 * @return String direccion html
	 */
	@GetMapping("/menu")
	public String volverAlMenu() {
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			resultado = "menu";
		}
		return resultado;
	}
}
