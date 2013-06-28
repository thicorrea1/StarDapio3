package com.example.stardapio.bean;

public class Type {
	private int id_type;
	private String type;
	private int id_restaurant;
	private String name;
	private String urlImage;
	
	public String toString() {
		return name;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public int getId_restaurant() {
		return id_restaurant;
	}

	public void setId_restaurant(int id_restaurant) {
		this.id_restaurant = id_restaurant;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId_type() {
		return id_type;
	}

	public void setId_type(int id_type) {
		this.id_type = id_type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
