package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Pedido;

@Service
public class PedidoService {
	@Autowired 
	private UsuarioService servicioUsuario;
	
	private List<Pedido> pedidosList = new ArrayList<>();

	public List<Pedido> findAll() {
		return pedidosList;
	}
	
	@PostConstruct
	public void init() {
		pedidosList.addAll(Arrays.asList(new Pedido(servicioUsuario.obtenerUsuario("F123")),new Pedido(servicioUsuario.obtenerUsuario("J123"))));
	}
}
