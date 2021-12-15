package com.example.demo.model;

import java.util.Comparator;

public class ComparatorFecha implements Comparator<Pedido> {
	
	/**
	 * Comparator para ordenar pedidos por fechas
	 */
    @Override
    public int compare(Pedido p1, Pedido p2) {
    	return p1.getFechaPedido().compareTo(p2.getFechaPedido());
    }
}