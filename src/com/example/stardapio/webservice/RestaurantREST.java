package com.example.stardapio.webservice;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.example.stardapio.bean.Item;
import com.example.stardapio.bean.Restaurant;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class RestaurantREST {

	private static final String URL_WSRESTAURANTE = "http://10.0.2.2:8080/StarDapioREST/restaurante/";
	private static final String URL_WSMENU = "http://10.0.2.2:8080/StarDapioREST/menu/";

	public List<Restaurant> getListaRestaurante() throws Exception {

		String[] resposta = new WebServiceRestaurant().get(URL_WSRESTAURANTE
				+ "buscarTodosGSON");

		if (resposta[0].equals("200")) {
			Gson gson = new Gson();
			List<Restaurant> listaRestaurante;

			JsonParser parser = new JsonParser();
			JsonArray array = parser.parse(resposta[1]).getAsJsonArray();

			Type collectionType = new TypeToken<List<Restaurant>>() {
			}.getType();
			listaRestaurante = gson.fromJson(array, collectionType);

			return listaRestaurante;

		} else {
			throw new Exception(resposta[1]);
		}
	}

	public List<Item> getListaItem(String idRestaurante) throws Exception {

		String[] resposta = new WebServiceRestaurant().get(URL_WSMENU + "GSON/"
				+ idRestaurante);

		if (resposta[0].equals("200")) {
			Gson gson = new Gson();
			ArrayList<Item> listaItem;
			JsonParser parser = new JsonParser();
			JsonArray array = parser.parse(resposta[1]).getAsJsonArray();

			Type collectionType = new TypeToken<List<Item>>() {
			}.getType();
			listaItem = gson.fromJson(array, collectionType);

			return listaItem;

		} else {
			throw new Exception(resposta[1]);
		}
	}

}