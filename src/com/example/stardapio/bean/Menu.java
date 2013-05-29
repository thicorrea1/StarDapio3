package com.example.stardapio.bean;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	private List<Item> itens;

	public Menu() {
		itens = new ArrayList<Item>();
	}

	public void addItem(Item item) {
		itens.add(item);
	}

	public List<Item> getItens() {
		return itens;
	}

}