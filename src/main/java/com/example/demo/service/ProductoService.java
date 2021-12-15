package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.example.demo.model.Producto;

@Service
public class ProductoService {

	private List<Producto> productosList = new ArrayList<>();

	/**
	 * Metodo para obtener todos los productos almacenados en el servidor
	 * @return La lista de productos
	 */
	public List<Producto> findAll() {
		return productosList;
	}
	
	/**
	 * Metodo para obtener un producto mediante su id
	 * @param Id del producto que deseamos obtener
	 * @return El producto deseado
	 */
	public Producto obtenerProductoPorId(String id){
		Producto p = null;
		for(Producto producto: this.productosList) {
			if(producto.getId().equals(id)) {
				p = producto;
			}
		}
		return p;
	}
	
	/**
	 * Metodo para recorrer la lista de productos y almacenar cada producto comprado junto a su cantidad en un hashmap
	 * @param Array con las cantidades de los productos comprados por el usuario 
	 * @return Un hasmap con los productos comprados como clavo y la cantidad de estos como valor
	 */
	public Map<Producto,Integer> obtenerHashMap(int[] cantidades){
		HashMap<Producto,Integer> resultado = new HashMap<>();
		//Recorro los productos del servidor y añado las cantidades de compra del usuario por orden
		for (int i = 0; i < this.productosList.size(); i++) {
			if(cantidades[i] > 0) {
				resultado.put(this.productosList.get(i), cantidades[i]);
			}
		}
		return resultado;
	}
	
	/**
	 * Metodo para calcular el precio total a pagar en un pedido
	 * @param HashMap con los productos y sus cantidades
	 * @return El precio total a pagar por todos los productos
	 */
	public double obtenerPrecioTotal(Map<Producto,Integer> productos) {
		double precioTotal = 0;
		//Recorro el hashmap de productos y cantidades, y voy sumando el total del precio de los productos por su cantidad
		for (Map.Entry<Producto, Integer> producto : productos.entrySet()) {
		    precioTotal += producto.getKey().getPrecio()*producto.getValue() ;
		}
		return precioTotal;
	}
	
	/**
	 * Metodo postConstruct para añadir algunos productos de forma estática para pruebas de desarrollo
	 */
	@PostConstruct
	public void init() {
		this.productosList.addAll( Arrays.asList (new Producto("111A","Camiseta","camiseta.jpg", 6.99),new Producto("222B","Sudadera","sudadera.jpg", 26.50),new Producto("333C","Botines","botines.jpg", 36.25),new Producto("444D","Gorro","gorro.jpg", 5.99), new Producto("555E","Pendiente","pendiente.jpg", 2.50),new Producto("666F","Pantalon","pantalon.jpg", 19.99) ));
	}
	
}