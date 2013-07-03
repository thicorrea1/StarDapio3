package com.example.stardapio;

import android.app.Application;

public class MyApp extends Application {

	// instance
	private static MyApp instance = null;

	private static String mesa = null;

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
	}
}
