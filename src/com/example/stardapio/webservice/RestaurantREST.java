package com.example.stardapio.webservice;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.example.stardapio.bean.ContainerTypeAndSubType;
import com.example.stardapio.bean.Item;
import com.example.stardapio.bean.Pedido;
import com.example.stardapio.bean.Restaurant;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class RestaurantREST {
	// private static final String URL_WSRESTAURANTE =
	// "http://stardapio.zapto.org/StarDapioREST/restaurante/";
	// private static final String URL_WSMENU =
	// "http://stardapio.zapto.org/StarDapioREST/menu/";
	private static final String dominio = "http://stardapio.com.br/rest/";

	private static final String URL_WSRESTAURANTE = dominio + "restaurante/";
	private static final String URL_WSMENU = dominio + "menu/";
	private static final String URL_WSPEDIDO = dominio + "pedido/";

	// private static final String URL_WSRESTAURANTE =
	// "http://192.168.1.32:8081/StarDapioREST/restaurante/";
	// private static final String URL_WSMENU =
	// "http://192.168.1.32:8081/StarDapioREST/menu/";
	// private static final String URL_WSPEDIDO =
	// "http://192.168.1.32:8081/StarDapioREST/pedido/";

	// private static final String URL_WSRESTAURANTE =
	// "http://192.168.1.33:8080/StarDapioREST/restaurante/";
	// private static final String URL_WSMENU =
	// "http://192.168.1.33:8080/StarDapioREST/menu/";

	// private static final String URL_WSRESTAURANTE =
	// "http://10.0.2.2:8080/StarDapioREST/restaurante/";
	// private static final String URL_WSMENU =
	// "http://10.0.2.2:8080/StarDapioREST/menu/";

	public String addPedido(Pedido pedido) throws Exception {
		// Log.i("ADD", "IDCLIENTE: " + pedido.getIdCliente() +
		// "IDRESTAURANTE: " + pedido.getIdRestaurant() + "ITEM: " +
		// pedido.getItens().get(0).getName() + "MESA: " + pedido.getMesa());
		Gson gson = new Gson();
		String pedidoJSON = gson.toJson(pedido);
		String[] resposta = new WebServiceRestaurant().post(URL_WSPEDIDO
				+ "adiciona", pedidoJSON);
		Log.i("ADD", pedidoJSON);
		if (resposta[0].equals("200")) {
			return resposta[1];
		} else {
			throw new Exception(resposta[1]);
		}
	}

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
		Log.i("ID_RESTAURANTE", idRestaurante);
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

	public List<Item> getListaItemType(String idType, String idRestaurant)
			throws Exception {
		String[] resposta = new WebServiceRestaurant().get(URL_WSMENU
				+ "GSON/restaurant/" + idRestaurant + "/type/" + idType);
		Log.i("ID_RESTAURANTE", idType);
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

	public List<com.example.stardapio.bean.Type> getListaType(
			String idRestaurante) throws Exception {

		String[] resposta = new WebServiceRestaurant().get(URL_WSMENU
				+ "types/" + idRestaurante);
		Log.i("ID_RESTAURANTE", idRestaurante);
		if (resposta[0].equals("200")) {
			Gson gson = new Gson();
			ArrayList<com.example.stardapio.bean.Type> listaType;
			JsonParser parser = new JsonParser();
			JsonArray array = parser.parse(resposta[1]).getAsJsonArray();

			Type collectionType = new TypeToken<List<com.example.stardapio.bean.Type>>() {
			}.getType();
			listaType = gson.fromJson(array, collectionType);

			return listaType;

		} else {
			throw new Exception(resposta[1]);
		}
	}

	public ContainerTypeAndSubType getListaTypeAndSubType(String idRestaurante)
			throws Exception {
		String[] resposta = new WebServiceRestaurant().get(URL_WSMENU
				+ "GSON/subtypes/restaurant/" + idRestaurante);
		Log.i("ID_RESTAURANTE", idRestaurante);
		if (resposta[0].equals("200")) {
			Gson gson = new Gson();
			ContainerTypeAndSubType container;
			JsonParser parser = new JsonParser();
			// JsonArray array = parser.parse(resposta[1]).getAsJsonArray();

			Type collectionType = new TypeToken<ContainerTypeAndSubType>() {
			}.getType();
			// container = gson.fromJson(array, collectionType);
			container = gson
					.fromJson(parser.parse(resposta[1]), collectionType);

			return container;

		} else {
			throw new Exception(resposta[1]);
		}
	}
}