package com.example.stardapio;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.util.Log;

import com.example.stardapio.bean.Pedido;

public class MyApp extends Application {

	// instance
	private static MyApp instance = null;

	private static String account = null;

	private static String mesa = null;
	
	private static Pedido pedido = null;

	public static String getAccount() {
		return account;
	}

	public static void setAccount(String account) {
		MyApp.account = account;
	}

	public static Pedido getPedido() {
		return pedido;
	}

	public static void setPedido(Pedido pedido) {
		MyApp.pedido = pedido;
	}

	public static String getMesa() {
		return mesa;
	}

	public static void setMesa(String mesa) {
		MyApp.mesa = mesa;
	}

	private static void checkInstance() {
		if (instance == null)
			throw new IllegalStateException("Application not created yet!");
	}

	public static MyApp getInstance() {
		checkInstance();
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// provide an instance for our static accessors
		instance = this;
		AccountManager am = AccountManager.get(this);
		Account[] accounts = am.getAccountsByType("com.google");
		account = accounts[0].name;
		Log.i("TAG", "Name: " + accounts[0].name + " Type: " + accounts[0].type);
		pedido = new Pedido();
		pedido.setIdCliente(account);
		// pedido = new Pedido(accounts[0].name, idRestaurante, MyApp.mesa);
	}
}
