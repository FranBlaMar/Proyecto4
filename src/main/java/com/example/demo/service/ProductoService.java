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

	public List<Producto> findAll() {
		return productosList;
	}
	
	public Producto obtenerProductoPorId(String id){
		Producto p = null;
		for(Producto producto: this.productosList) {
			if(producto.getId().equals(id)) {
				p = producto;
			}
		}
		return p;
	}
	
	public Map<Producto,Integer> obtenerHashMap(int[] cantidades){
		HashMap<Producto,Integer> resultado = new HashMap<>();
		for (int i = 0; i < this.productosList.size(); i++) {
			if(cantidades[i] > 0) {
				resultado.put(this.productosList.get(i), cantidades[i]);
			}
		}
		return resultado;
	}
	
	public double obtenerPrecioTotal(Map<Producto,Integer> productos) {
		double precioTotal = 0;
		for (Map.Entry<Producto, Integer> producto : productos.entrySet()) {
		    precioTotal += producto.getKey().getPrecio()*producto.getValue() ;
		}
		return precioTotal;
	}
	
	@PostConstruct
	public void init() {
		this.productosList.addAll( Arrays.asList (new Producto("111A","Camiseta","camiseta.jpg", 6.99),new Producto("222B","Sudadera","sudadera.jpg", 26.50),new Producto("333C","Botines","botines.jpg", 36.25),new Producto("444D","Gorro","gorro.jpg", 5.99), new Producto("555E","Pendiente","pendiente.jpg", 2.50),new Producto("666F","Pantalon","pantalon.jpg", 19.99) ));
	}
	
}