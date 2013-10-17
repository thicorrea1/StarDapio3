package com.example.stardapio.bean;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
	private long idPedido;
	private List<Item> itens;
	private String idCliente;
	private long mesa;
	private long idRestaurant;
	private int coluna;

	public Pedido() {
		itens = new ArrayList<Item>();
		coluna = 1;
	}

	public Pedido(String idCliente, long idRestaurante, String mesa) {
		this.idCliente = idCliente;
		this.idRestaurant = idRestaurante;
		this.mesa = Long.parseLong(mesa);
	}

	public void deletePedido(Item pedido) {
		itens.remove(pedido);
	}

	public long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(long idPedido) {
		this.idPedido = idPedido;
	}

	public void addItem(Item item) {
		itens.add(item);
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public long getMesa() {
		return mesa;
	}

	public void setMesa(long mesa) {
		this.mesa = mesa;
	}

	public long getIdRestaurant() {
		return idRestaurant;
	}

	public void setIdRestaurant(long idRestaurant) {
		this.idRestaurant = idRestaurant;
	}
	
	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	@Override
	public String toString() {
		return itens.toString();
	}
	
	@Override
	public int hashCode() {
		return (int) idPedido;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj instanceof Pedido)
				&& (((Pedido) obj).getIdPedido() == this.idPedido)) {
			return true;
		} else {
			return false;
		}
	}
}
